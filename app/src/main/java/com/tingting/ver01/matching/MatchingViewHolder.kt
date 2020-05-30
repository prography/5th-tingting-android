package com.tingting.ver01.matching

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.model.matching.ShowAllCandidateListResponse
import com.tingting.ver01.viewModel.MatchingFragmentViewModel
import kotlinx.android.synthetic.main.recycler_item_matching4.view.*
import kotlinx.android.synthetic.main.recycler_item_matching4.view.arrowToDetail
import kotlinx.android.synthetic.main.recycler_item_matching4.view.tags

class MatchingViewHolder constructor(var dataBinding: ViewDataBinding, var matchingFragmentViewModel: MatchingFragmentViewModel) : RecyclerView.ViewHolder(dataBinding.root){

    var img1 = itemView.img1
    var img2 = itemView.img2
    var img3 = itemView.img3
    var img4 = itemView.img4
    var arrow = itemView.arrowToDetail

    val tagText = itemView.tags

    fun setHolder( item : ShowAllCandidateListResponse.Data.Matching) {
        dataBinding.setVariable(BR.matchTeamItem, item)
        dataBinding.executePendingBindings()
        var num = 0;

        for (i in item.membersInfo.size - 1 downTo 0) {

            when (num) {
                0 -> MainActivity.glide.setImage(
                    img1.context,
                    MainActivity.glide.DecryptUrl(item.membersInfo.get(i).thumbnail), img1
                )

                1 -> MainActivity.glide.setImage(
                    img2.context,
                    MainActivity.glide.DecryptUrl(item.membersInfo.get(i).thumbnail), img2
                )

                2 -> MainActivity.glide.setImage(
                    img3.context,
                    MainActivity.glide.DecryptUrl(item.membersInfo.get(i).thumbnail), img3
                )

                3 -> MainActivity.glide.setImage(
                    img4.context,
                    MainActivity.glide.DecryptUrl(item.membersInfo.get(i).thumbnail), img4
                )
            }
            num++;
        }


        var tagString = "";

        for (i in 0..item.tags.size - 1) {
            tagString += "#" + item.tags.get(i) + " "
        }

        tagText.setText(tagString)

        when (item.max_member_number) {
            1 -> {
                img1.visibility = View.VISIBLE
                img2.visibility = View.GONE
                img3.visibility = View.GONE
                img4.visibility = View.GONE
            }

            2 -> {
                img1.visibility = View.VISIBLE
                img2.visibility = View.VISIBLE
                img3.visibility = View.GONE
                img4.visibility = View.GONE

            }

            3 -> {
                img1.visibility = View.VISIBLE
                img2.visibility = View.VISIBLE
                img3.visibility = View.VISIBLE
                img4.visibility = View.GONE

            }

            4 -> {
                img1.visibility = View.VISIBLE
                img2.visibility = View.VISIBLE
                img3.visibility = View.VISIBLE
                img4.visibility = View.VISIBLE

            }
        }
    }
}