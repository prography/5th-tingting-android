package com.tingting.ver01.searchTeam

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.tingting.ver01.R
import com.tingting.ver01.databinding.CurrentMatchingTeamItem4Binding
import com.tingting.ver01.model.team.lookTeamList.TeamResponse
import com.tingting.ver01.searchTeam.RecylcerViewLoad.TeamDiffUtilCallback
import com.tingting.ver01.view.Main.SearchTeamFragment
import com.tingting.ver01.viewModel.SearchTeamFragmentViewModel
import kotlinx.android.synthetic.main.dialog_team_password.view.*

class SearchTeamAdapter(
    private val searchTeamFragmentViewModel: SearchTeamFragmentViewModel,
   var context: Context
) : PagedListAdapter<TeamResponse, SearchTeamViewHolder>(TeamDiffUtilCallback()) {

    lateinit var inflater : LayoutInflater
    lateinit var parentViewGroup : ViewGroup

    companion object{
        var teamId = 0
    }

    private var searchListData: MutableList<TeamResponse.Data.Team> = searchTeamFragmentViewModel.itemArray

    //모든 view는context를 가지고있다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTeamViewHolder {
        parentViewGroup = parent
        inflater = LayoutInflater.from(parent.context)
        val dataBinding = CurrentMatchingTeamItem4Binding.inflate(inflater, parent, false)

        return SearchTeamViewHolder(dataBinding, SearchTeamFragmentViewModel())
    }

    override fun getItemCount(): Int {
        return searchListData.size
    }

    override fun getItemId(position: Int): Long {
        return searchListData[position].id.hashCode().toLong()
    }

    override fun onBindViewHolder(holder: SearchTeamViewHolder, position: Int) {

        Log.d("getItemSize", searchListData.size.toString())

        holder.setup(searchListData[position])

        holder.arrowToDetail.setOnClickListener {
            if (searchListData.get(position).hasPassword) {

                val entryDialog = AlertDialog.Builder(parentViewGroup.context)

                val screen =  inflater.inflate(R.layout.dialog_team_password, null,false)

                val mbuilder = entryDialog.setView(screen).show()


                val pinDialog = screen.pin_entry

                pinDialog.focus()

                if(pinDialog!=null){
                    screen.pin_entry.setOnPinEnteredListener (PinEntryEditText.OnPinEnteredListener{
                        val resultCode = searchTeamFragmentViewModel.teamJoin(searchListData.get(position).id
                            ,it.toString(),parentViewGroup.context)

                        mbuilder.dismiss()
                    })

                }

                screen.dialogCancel.setOnClickListener {
                    mbuilder.dismiss()
                }

            } else {
                val searchTeamDetailIntent =
                    Intent(context.applicationContext, SearchTeamInfo::class.java)
                searchTeamDetailIntent.putExtra("teamBossId", searchListData.get(position).id)
                teamId = searchListData.get(position).id

                parentViewGroup.context.startActivity(searchTeamDetailIntent)
            }
        }

    }

    fun updateData(data: java.util.ArrayList<TeamResponse?>, number: Int) {
        this.searchListData.clear()

        for(j in 0..data.size-1){

            if (!data.get(j)?.data?.teamList?.isEmpty()!!) {
                if (number == 0) {
                    for (i in 0..data.get(j)?.data?.teamList?.size!! - 1) {
                        this.searchListData.add(data.get(j)!!.data.teamList.get(i))
                    }
                } else {
                    for (i in 0..data.get(j)?.data?.teamList?.size!! - 1) {
                        if (number == data.get(j)!!.data.teamList.get(i).max_member_number) {
                            this.searchListData.add(data.get(j)!!.data.teamList.get(i))
                        }
                    }
                }
            }

        }
        notifyDataSetChanged()
    }

    fun noti(){
        notifyDataSetChanged()
    }

}