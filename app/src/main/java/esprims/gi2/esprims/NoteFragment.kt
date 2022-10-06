package esprims.gi2.esprims

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import esprims.gi2.esprims.databinding.FragmentNoteBinding
import esprims.gi2.esprims.model.Matiere
import esprims.gi2.esprims.model.MatiereAdapter


class NoteFragment : Fragment() {

    private  lateinit var binding:FragmentNoteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("test",(activity as MainActivity).gradeUID.toString() )


        var matiereAdapter=MatiereAdapter(mutableListOf())
        binding.noteRV.apply {
            layoutManager=LinearLayoutManager(requireActivity())
            adapter=matiereAdapter
        }

        val db = Firebase.firestore
        db.collection("matiere").whereEqualTo("class_id", (activity as MainActivity).gradeUID)
            .addSnapshotListener{ value,error->

             error?.let {
                 Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                 return@addSnapshotListener
             }

                val matieres= mutableListOf<Matiere>()
                value?.let {

                    for (document in value.documents){

                        val matiere=document.toObject<Matiere>()
                        matieres.add(matiere!!)
                        Log.d("test", "${matiere.toString()}")


                    }
                }


                Log.d("test","${matieres.toString()}")
                 binding.noteRV.adapter= MatiereAdapter(matieres)
                matiereAdapter.notifyDataSetChanged()


    }


}


}

