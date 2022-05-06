package esprims.gi2.esprims

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import esprims.gi2.esprims.databinding.ActualityItemBinding
import esprims.gi2.esprims.model.Actuality

class ActualityAdapter(
   val  context:Context,
var dataSEt: MutableList<Actuality> ,
) : RecyclerView.Adapter<ActualityAdapter.ActualityViewHolder>() {
    inner class ActualityViewHolder(val binding: ActualityItemBinding) :
        RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActualityViewHolder {


        return ActualityViewHolder(
            ActualityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ActualityViewHolder, position: Int) {
        val imagePAth = dataSEt[position].path

        Glide.with(context).load(imagePAth!!.toUri()).into(holder.binding.actualityIV)



    }


    override fun getItemCount(): Int {
        return dataSEt.size
    }


}

