package com.tingting.ver01.matching.matchingTeamMemberProfileDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tingting.ver01.BR
import com.tingting.ver01.view.Main.MainActivity
import com.tingting.ver01.databinding.FragmentMatchingViewpagerBinding
import com.tingting.ver01.model.profile.GetTeammberProfileResponse
import com.tingting.ver01.viewModel.FirstPagerFragmentViewModel
import java.util.*

class TeamMemeberProfilePager (id : Int) : Fragment() {
    var teamMemberId = id

    lateinit var dataBinding : FragmentMatchingViewpagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //화면 높이를 바꿔줘야 하는데
        dataBinding = FragmentMatchingViewpagerBinding.inflate(inflater,container,false).apply {
            viewmodel = ViewModelProviders.of(this@TeamMemeberProfilePager).get(FirstPagerFragmentViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }

        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Pager실행", "pagerrrrrr")
        dataBinding.viewmodel?.fetchData(teamMemberId)
        observer()
    }

    fun observer(){
        dataBinding.viewmodel?.indivisualProfile?.observe(this, androidx.lifecycle.Observer {
            setImage(it)
        })
    }

    fun setImage(item: GetTeammberProfileResponse) {
        dataBinding.setVariable(BR.teamMemberData, item)
        dataBinding.executePendingBindings()

        dataBinding.teammemberAge.text = calAge(item.data.userInfo.birth)

        MainActivity.glide.setSquareImage(
            dataBinding.profileImage.context,
            MainActivity.glide.DecryptUrl(item.data.userInfo.thumbnail),
            dataBinding.profileImage
        )

    }

    fun calAge(age: String): String {
        var year = Calendar.getInstance().get(Calendar.YEAR)
        var b = age.substring(0, 4)
        var peopleAge = year - b.toInt() + 1

        return peopleAge.toString()
    }


}