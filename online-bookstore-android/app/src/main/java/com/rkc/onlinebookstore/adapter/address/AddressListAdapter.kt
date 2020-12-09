package com.rkc.onlinebookstore.adapter.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rkc.onlinebookstore.R
import com.rkc.onlinebookstore.model.user.Address
import com.rkc.onlinebookstore.view.home.mine.address.OPERATION_EDIT
import com.rkc.onlinebookstore.view.home.mine.address.OPERATION_TYPE
import com.rkc.onlinebookstore.viewmodel.home.mine.address.AddressViewModel
import kotlinx.android.synthetic.main.address_item.view.*

/**
 * @author rkc
 * @date 2020/11/19 17:35
 * @version 1.0
 */
const val ADDRESS_BUNDLE_KEY = "address_key"

class AddressListAdapter(private var defaultId: Int, private var addressViewModel: AddressViewModel) : ListAdapter<Address, AddressListViewHolder>(DiffCallBack) {
    object DiffCallBack : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressListViewHolder {
        val holder = AddressListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.address_item, parent, false))
        holder.itemView.edit.setOnClickListener {
            val address = getItem(holder.absoluteAdapterPosition)
            Bundle().apply {
                putParcelable(ADDRESS_BUNDLE_KEY, address)
                putInt(OPERATION_TYPE, OPERATION_EDIT)
                //跳转到编辑页面
                holder.itemView.findNavController().navigate(R.id.action_addressFragment_to_addressEditFragment, this)
            }
        }
        holder.itemView.setDefault.setOnClickListener {
            val address = getItem(holder.absoluteAdapterPosition)
            //发起请求修改默认地址
            addressViewModel.setDefaultAddress(address.id)
        }
        return holder
    }

    override fun onBindViewHolder(holder: AddressListViewHolder, position: Int) {
        val address = getItem(position) ?: return
        with(holder.itemView) {
            nicknameET.text = address.receiverName
            phone.text = address.phone
            addressInfo.text = address.address
            if (defaultId == address.id) {
                //显示默认
                isDefaultAddress.visibility = View.VISIBLE
            } else {
                isDefaultAddress.visibility = View.GONE
            }
        }
    }
}

class AddressListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)