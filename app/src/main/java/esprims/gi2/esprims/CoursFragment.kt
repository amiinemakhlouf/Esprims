package esprims.gi2.esprims

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import esprims.gi2.esprims.databinding.FragmentCoursBinding
import esprims.gi2.esprims.model.Matiere


class CoursFragment : Fragment() {
    private  lateinit var  binding:FragmentCoursBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCoursBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = Firebase.firestore

        db.collection("matiere").whereEqualTo(
            "class_id",0
        ).addSnapshotListener{ value,error->

            error?.let {
                Log.d("testit","an error has occured")
            }

            value?.let {
                val listOfMatiere= mutableListOf<Matiere>()
                for (document in value.documents){

                    val matiere= document.toObject<Matiere>()
                    listOfMatiere.add(matiere!!)
                }
                val listofMatieresName=listOfMatiere.map { it.nom }
                val arrayAdapter= ArrayAdapter(requireActivity(),android.R.layout.simple_list_item_1,listofMatieresName)
                binding.lv.adapter= arrayAdapter
            }


        }


    }
}