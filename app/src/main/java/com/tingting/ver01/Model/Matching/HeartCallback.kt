package com.tingting.ver01.Model.Matching

interface HeartCallback {

    // 최초 매칭 후보
    fun FirstSendHeart(data:FirstSendHeartResponse){

    }

    // 우리팀 멤버 모두 동의한 경우
    fun SendHeart(data:SendHeartResponse){

    }

    // 우리팀이 받은 하트 수락하기
    fun ReceiveHeart(data:ReceiveHeartResponse){

    }
}