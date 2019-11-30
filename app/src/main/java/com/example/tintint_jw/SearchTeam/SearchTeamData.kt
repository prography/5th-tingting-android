package com.example.tintint_jw.SearchTeam

class SearchTeamData {

    var img1: Int = 0
    var img2: Int = 0
    var img3: Int = 0
    var img4: Int = 0
    var count: Int = 0;
    var text: String = ""
    var key: String = ""

    constructor(img1: Int, text: String, count :Int) {
        this.key = "one"
        this.img1 = img1
        this.text = text
        this.count=1;
    }

    constructor(img1: Int, img2: Int, text: String, count :Int) {
        this.key = "two"
        this.img1 = img1
        this.img2 = img2
        this.text = text
        this.count=2;
    }

    constructor(img1: Int, img2: Int, img3: Int, text: String, count :Int) {

        this.key = "three"
        this.img1 = img1
        this.img2 = img2
        this.img3 = img3
        this.text = text
        this.count=3;

    }

    constructor(img1: Int, img2: Int, img3: Int, img4: Int, text: String, count :Int) {
        this.key = "four"
        this.img1 = img1
        this.img2 = img2
        this.img3 = img3
        this.img4 = img4
        this.text = text
        this.count=4;
    }
}