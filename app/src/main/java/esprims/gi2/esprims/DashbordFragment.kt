package esprims.gi2.esprims

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.otaliastudios.opengl.core.use
import esprims.gi2.esprims.databinding.FragmentDashbordBinding
import esprims.gi2.esprims.databinding.HeaderNavigationDrawerBinding
import esprims.gi2.esprims.model.Grade
import esprims.gi2.esprims.model.User


class DashbordFragment : Fragment() {
    private lateinit var binding: FragmentDashbordBinding
    private lateinit var  headerBinding:HeaderNavigationDrawerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashbordBinding.inflate(layoutInflater)
        headerBinding= HeaderNavigationDrawerBinding.inflate(layoutInflater)
        return binding.root
    }
   val args:  DashbordFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Firebase.firestore
        var userName:String?=null
        var gradeName:String? =null
        val uid =args.userId
        var gradeUid:Int?=null
        Log.d("TAG", uid)
        var imageUrl:String?=null

        db.collection("user")
            .whereEqualTo("id", uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {


                    val user = document.toObject<User>()
                    gradeUid=user.class_id!!
                    userName=user.name!!
                    Log.d("TAG", gradeUid.toString())
                    Log.d("TAG", userName!!)

                    db.collection("grade")
                        .whereEqualTo("id", gradeUid)
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val grade = document.toObject<Grade>()
                              imageUrl=grade.emploi
                                gradeName=grade.name
                                Glide.with(requireActivity())
                                    .load(grade.emploi?.toUri())
                                    .into(binding.emploi)

                            }
                            handleHeaderNAvigation(userName,gradeName)
                        }
                        .addOnFailureListener { exception ->
                            Log.d("TAG", "no mistake")

                        }





                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireActivity(),"bad",Toast.LENGTH_LONG).show()
                Log.d("TAG", "no mistake")

            }


        Toast.makeText(activity, imageUrl,Toast.LENGTH_LONG).show()



        Log.d("test",uid)




    }

    private fun handleHeaderNAvigation(name:String?,grade:String?) {
        val viewHeader = (activity as MainActivity).binding.navView.getHeaderView(0)

// nav_header.xml is headerLayout
            val navViewHeaderBinding =  HeaderNavigationDrawerBinding.bind(viewHeader)

// title is Children of nav_header
        navViewHeaderBinding.name.text = name

        navViewHeaderBinding.grade.text = grade

    }
}