package com.bueno.alvaro.laboratoriocalificado03

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bueno.alvaro.laboratoriocalificado03.databinding.ItemTeacherBinding
import com.bumptech.glide.Glide

class TeacherAdapter(
    var list: List<TeacherResponse>
) : RecyclerView.Adapter<TeacherAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTeacherBinding.bind(view)

        fun bind(teacher: TeacherResponse) {
            binding.tvName.text = "${teacher.name} ${teacher.last_name}"
            Glide.with(itemView)
                .load(teacher.imageUrl)
                .into(binding.ivPhoto)

            // Click simple: Llamar
            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${teacher.phone}")
                }
                it.context.startActivity(intent)
            }

            // Click largo: Enviar correo
            itemView.setOnLongClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${teacher.email}")
                }
                it.context.startActivity(intent)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val teacher = list[position]
        holder.bind(teacher)
    }

    override fun getItemCount(): Int = list.size
}
