package esprims.gi2.esprims.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esprims.gi2.esprims.databinding.MatiereItemBinding


class MatiereAdapter(
    var dataSEt: MutableList<Matiere>
) : RecyclerView.Adapter<MatiereAdapter.MatiereViewHolder>() {
    inner class MatiereViewHolder(val binding: MatiereItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatiereViewHolder {


        return MatiereViewHolder(
            MatiereItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: MatiereViewHolder, position: Int) {
        holder.binding.apply {

            matiere.text = dataSEt[position].nom
            dataSEt[position].noteDs?.let {
                noteDS.text=" note ds :"+it.toString()
            }?: run {
                noteDS.text = "note ds "
            }
            noteEx.text = "note examin : " +dataSEt[position].noteEX
            noteTP.text =  " note TP :" +dataSEt[position].noteTP
            absence.text= "taux d'absences "+dataSEt[position].absence+"%"

        }
    }


    override fun getItemCount(): Int {
        return dataSEt.size
    }
}