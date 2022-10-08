package esprims.gi2.esprims

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import esprims.gi2.esprims.databinding.FragmentActualityBinding
import esprims.gi2.esprims.model.Actuality


class ActualityFragment : Fragment() {
    private lateinit var binding: FragmentActualityBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentActualityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var actualityRV = binding.actualiteRV
        val adapter = ActualityAdapter(requireActivity(),mutableListOf())
        actualityRV.layoutManager = LinearLayoutManager(requireActivity())
     fdsfs
        actualityRV.adapter = adapter

        val db = Firebase.firestore

        db.collection("actualite").addSnapshotListener { value, error ->

            value?.let {

                when {
                else -> {

                    binding.myProgressBar.isVisible=false

                        val actualities = mutableListOf<Actuality>()
                        for (document in value.documents) {



                            val actualite = document.toObject<Actuality>()
                            actualities.add(actualite!!)
                            Log.d("ActualityFR", actualite.path.toString())

                        }

                        adapter.dataSEt = actualities
                        Log.d("ActualityFR", adapter.dataSEt.toString())



                        adapter.notifyDataSetChanged()


                    }

                }
            }


        }


    }
}