package esprims.gi2.esprims

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import esprims.gi2.esprims.databinding.FragmentPayementBinding
import esprims.gi2.esprims.model.Payement


class PayementFragment : Fragment() {

    private lateinit var binding: FragmentPayementBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPayementBinding.inflate(layoutInflater)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).binding.toolbae.isVisible=true

        val db = FirebaseFirestore.getInstance()
        db.collection("payement").whereEqualTo("userID", Firebase.auth.currentUser!!.uid)
            .get().addOnSuccessListener{ value ->


                    val document= value.documents[0]
                    val payment = document.toObject<Payement>()
                    if(payment!!.premiereTranche==true)
                    {
                        binding.checkBox.isChecked=true
                    }
                    if(payment.deuxiemeTranche==true)
                    {
                        binding.checkBox2.isChecked=true
                    }
                    if(payment.troisiemeTranche==true){

                        binding.checkBox3.isChecked=true

                    }

                }

            }


    }
