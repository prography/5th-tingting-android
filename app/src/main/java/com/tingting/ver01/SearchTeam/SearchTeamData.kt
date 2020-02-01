package com.tingting.ver01.SearchTeam

class SearchTeamData {

    var img1: String = ""
    var img2: String = ""
    var img3: String = ""
    var img4: String = ""
    var count: Int = 0;
    var text: String = ""
    var key: String = ""
    var place:String =""

    constructor(img1: String, place:String, text: String, count: Int) {
        this.key = "one"
        this.img1 = img1
        this.text = text
        this.count = 1;
        this.place = place
    }

    constructor(img1: String, img2: String, place:String, text: String, count: Int) {
        this.key = "two"
        this.img1 = img1
        this.img2 = img2
        this.text = text
        this.count = 2;
        this.place = place

    }

    constructor(img1: String, img2: String, img3: String, place:String, text: String, count: Int) {

        this.key = "three"
        this.img1 = img1
        this.img2 = img2
        this.img3 = img3
        this.text = text
        this.count = 3;
        this.place = place


    }

    constructor(img1: String, img2: String, img3: String, img4: String, place:String, text: String, count: Int) {
        this.key = "four"
        if (this.img1 != null) {
            img1
        }
        this.img2 = img2
        this.img3 = img3
        this.img4 = img4
        this.text = text
        this.count = 4;
        this.place = place

    }

    fun changedata(index: Int, url: String) {
        when (index) {
            0 -> this.img1 = url
            1 -> this.img2 = url
            2 -> this.img3 = url
            3 -> this.img4 = url
        }
    }

}