package esprims.gi2.esprims

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import esprims.gi2.esprims.databinding.FragmentLoginBinding
import java.util.regex.Pattern


class LoginFragment : Fragment() {
    private lateinit var viewModel: BaseViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var emailFlag = false
        var passwordFlag = false
        viewModel=ViewModelProvider(requireActivity()).get(BaseViewModel::class.java)
        (activity as MainActivity).binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        auth = Firebase.auth
        isUserConnected()

        (activity as MainActivity).binding.toolbae.isVisible=false





        binding.loginButton.setOnClickListener {

            val emailInput = binding.email.editText?.text.toString()
            if (!isValidEmail(emailInput)) {
                binding.email.error = "invalid email  "
                it.isActivated=true
            } else {
                binding.email.error = null
                emailFlag = true

            }

            val passwordInput = binding.password.editText?.text.toString()

            if (passwordInput.length < 8) {

                binding.password.error = "password should contain at least 8 carachters"
                it.isActivated=false
            } else {
                binding.password.error = null
                passwordFlag = true
            }

            if (passwordFlag and emailFlag) {

                auth = Firebase.auth
                signIn(emailInput, passwordInput)


            }


        }


    }

    override fun onResume() {
        super.onResume()
        // hideToolbar()

    }

    fun isValidEmail(email: CharSequence): Boolean {
        var isValid = true

        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        if (!matcher.matches()) {
            isValid = false
        }
        return isValid
    }

    fun signIn(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // Log.d(TAG, "signInWithEmail:success")

                    val uid = auth.uid
                    viewModel.userId= auth.currentUser!!.uid
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToDashbordFragment(uid!!)

                    findNavController().navigate(action)




                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    // updateUI(null)
                }
            }
    }


    override fun onStop() {
        super.onStop()

        //showToolbar()
    }


    /*fun hideToolbar() {
        val toolbar = (activity as MainActivity).binding.toolbae
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }


    fun showToolbar() {
        val toolbar = (activity as MainActivity).binding.toolbae
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.show()
    }*/

    fun isUserConnected() {
        if (auth.currentUser != null) {
            viewModel.userId= auth.currentUser!!.uid
            val action =
                LoginFragmentDirections.actionLoginFragmentToDashbordFragment(auth.currentUser!!.uid.toString())
            findNavController().navigate(action)
        }
    }


}