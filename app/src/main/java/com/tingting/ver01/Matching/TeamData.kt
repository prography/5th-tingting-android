package com.tingting.ver01.Matching

class TeamData {
    var key : String = ""
    var img1 : String = ""
    var img2 : String = ""
    var img3 : String = ""
    var img4 : String = ""
    var info1 :String = ""
    var place :String = ""

    constructor(img1:String, place:String, info1: String){
        this.key = "one"
        this.img1 = img1
        this.info1 = info1
        this.place = place
    }
    constructor(img1:String, img2:String,place:String, info1: String)
    {
        this.key = "two"
        this.img1 = img1
        this.img2 = img2
        this.info1 = info1
        this.place = place

    }
    constructor(img1:String, img2:String, img3:String,place:String, info1: String)
    {
        this.key = "three"
        this.img1 = img1
        this.img2 = img2
        this.img3 = img3
        this.info1 = info1
        this.place = place

    }
    constructor(img1:String, img2:String, img3: String, img4:String, place:String,info1: String)
    {
        this.key = "four"
        this.img1 = img1
        this.img2 = img2
        this.img3 = img3
        this.img4 = img4
        this.info1 = info1
        this.place = place

    }
}