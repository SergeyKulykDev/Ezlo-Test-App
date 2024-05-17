package com.serhiikulyk.ezlotestapp.ui.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.serhiikulyk.ezlotestapp.R
import com.serhiikulyk.ezlotestapp.databinding.ItemDeviceBinding

class DevicesAdapter(
    private val onClick: (DeviceItem) -> Unit,
    private val onLongClick: (DeviceItem) -> Unit
): ListAdapter<DeviceItem, DevicesAdapter.DeviceViewHolder>(DeviceItemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class DeviceViewHolder(private val binding: ItemDeviceBinding): ViewHolder(binding.root) {

        fun onBind(item: DeviceItem) = with(binding){
            icon.setImageResource(item.icon)
            title.text = item.title
            sn.text = sn.context.getString(R.string.device_sn, item.sn)
            root.setOnClickListener {
                onClick(item)
            }
            root.setOnLongClickListener {
                onLongClick(item)
                return@setOnLongClickListener true
            }
        }
    }

}

object DeviceItemDiffUtil: DiffUtil.ItemCallback<DeviceItem>() {
    override fun areItemsTheSame(oldItem: DeviceItem, newItem: DeviceItem): Boolean {
        return oldItem.sn == newItem.sn
    }

    override fun areContentsTheSame(oldItem: DeviceItem, newItem: DeviceItem): Boolean {
        return oldItem == newItem
    }

}
