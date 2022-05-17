package esprims.gi2.esprims

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import esprims.gi2.esprims.databinding.FragmentChapitreBinding
import esprims.gi2.esprims.model.Chapitre
import esprims.gi2.esprims.model.Matiere
import java.net.URL


class ChapitreFragment : Fragment() {
    private lateinit var  binding:FragmentChapitreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentChapitreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: ChapitreFragmentArgs by  navArgs()
        val amount = args.chapitre
        val db=Firebase.firestore

        var listOfUrl:MutableList<String> ? =null




        db.collection("matiere").whereEqualTo("nom",amount).get().addOnSuccessListener {

            val matiere=it.toObjects<Matiere>()

            Log.d("testit",matiere[0].id.toString())
            val id=matiere[0].id

        db.collection("chapitre").whereEqualTo("matiere_id",id).addSnapshotListener { value, error ->

            value?.let {
                val listOfChapitre= mutableListOf<Chapitre>()

                for(document in value.documents){

                    val chapitre=document.toObject<Chapitre>()
                    Log.d("testit",chapitre!!.cours_pdf!!)
                    listOfChapitre.add(chapitre)
                }
                val listOfChapterName =listOfChapitre.map { it.nom!! }
                listOfUrl =listOfChapitre.map { it.cours_pdf!! }.toMutableList()
                Log.d("testit",listOfChapitre.toString())
                val arrayAdapter= ArrayAdapter<String>(requireActivity(),android.R.layout.simple_list_item_1, listOfChapterName)
                binding.tv.adapter=arrayAdapter
            }
            error?.let {
                Log.d("testit","an error has occured")
            }

        }}

        binding.tv.setOnItemClickListener { parent, view, position, id ->

          val coursUrl=listOfUrl!![position]

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(coursUrl))
            startActivity(browserIntent)

        }
    }
}