package com.example.tintint_jw.Matching

class TeamData {
    var key : String = ""
    var img1 : Int = 0
    var img2 : Int = 0
    var img3 : Int = 0
    var img4 : Int = 0
    var info1 :String = ""

    constructor(img1:Int, info1: String){
        this.key = "one"
        this.img1 = img1
        this.info1 = info1
    }
    constructor(img1:Int, img2:Int, info1: String)
    {
        this.key = "two"
        this.img1 = img1
        this.img2 = img2
        this.info1 = info1
    }
    constructor(img1:Int, img2:Int, img3:Int, info1: String)
    {
        this.key = "three"
        this.img1 = img1
        this.img2 = img2
        this.img3 = img3
        this.info1 = info1
    }
    constructor(img1:Int, img2:Int, img3: Int, img4:Int, info1: String)
    {
        this.key = "four"
        this.img1 = img1
        this.img2 = img2
        this.img3 = img3
        this.img4 = img4
        this.info1 = info1
    }
}