package com.zxp.mvpcuoqv.kotlin

import android.app.Activity
import android.widget.Toast
import androidx.recyclerview.widget.SortedList
import java.lang.reflect.Constructor
import java.util.*


open class lian constructor() {
    /*  fun changliang():String{
          var i=0;
          var toString:String = i.toString()
          var string="你好"

          return "${string}${toString}"
      }*/


    /*  fun shuzhu():String{
          val intArrayOf = intArrayOf(1, 2, 3, 4)
          val arrayOf = arrayOf(1, "asd", 1.65, 0.1)
          val byteArrayOf = byteArrayOf(1, 2, 3)
          var int=""
          var string=""
          var byte=""
          intArrayOf.forEach {
              int="${int}${it}"
          }
          for (i in 0.. arrayOf.size-1){
              string="${string}${arrayOf[i]}"
          }
          for (i in 0 until byteArrayOf.size){
              byte="$byte${byteArrayOf[i]}"
          }
          return "${int}${string}$byte"
      }*/


    fun stringsba(): String {
        /* val str="agtywasafsaf";
         val get = str.get(4)
         val compareTo = str.compareTo("a")
         val split = str.split("y")
         val substring = str.substring(3)
         return "$get$compareTo$split$substring"*/

        /*    var  a : Array<Int> = arrayOf(3,5,1,7)
            Arrays.sort(a)
          var  string=""
          a.forEach {
              string += it
          }
          val mutableListOf = mutableListOf("b", "d", "q", "g", "c")
          val list=SortedList.
          mutableListOf.forEach(

          )*/
        var sortList = mutableListOf("a", "bb", "ccc", "s", "wwwwwwwwww")
        var desc = ""
        var cribe = ""
        val list = sortList.sortedBy { it.length }
        list.forEach {
            desc = desc + "," + it
        }
        val verseList = sortList.sortedByDescending { it.length }
        verseList.forEach {
            cribe = "${cribe}${","}${it}"
        }

        return "$desc    $cribe"
    }

    /*fun ifpanduan(): String {
        var str = ""
        var kong = "1"
        str = if (str.isNullOrBlank()) "为空" else "不为空"
        kong = if (kong.isNotBlank()) "有值" else "无值"

        return "$str$kong";
    }

    fun xunhuang(): String {
        var str = "atrdsa"
        val toCharArray = str.toCharArray()
        var shu = ""
        for (i in toCharArray.indices) {
            if (toCharArray[i].toString() == "s") continue
            if (toCharArray[i].toString() == "t") break
            for (j in toCharArray.indices) {
                shu += "${toCharArray[j]}    $j";
            }
        }
        return shu;
    }
    fun  leixingpand():String{
        val arrayOf = arrayOf("1", "2", "#", "4","5","6","&")
        var  Str=""
        for (i in arrayOf.indices){
           *//* if (arrayOf is Array<String>) continue*//*
          *//*  if ("6" in arrayOf) break*//*
            Str+="${arrayOf[i]}$i"
        }
        return Str
    }*/
    /*fun  whenxuanzhe():String{
        var arr=1;
        var  str=""
        when(arr){
            is Int-> str="true"
            else -> str="false"
        }
        return str
    }*/
  /*  fun hanshu(a :String ,b :String):Boolean=a.length>b.length*/
    /*fun kebianchanshu(vararg  s:String):String{
        var str=""
        for (i in s.indices){
            str+="${s[i]}$i${","}"
        }
        return str
    }*/
    fun<T> fanxinghdas(vararg  a :T?):String{
            var  str=""
        a.forEach {
            str+="$it${","}"

        }
        return str
    }
}

fun main() {
    /* val zuh:String = lian().changliang()
     val shuzhu = lian().shuzhu()*/
    /* val stringsba = lian().stringsba()
      print("$zuh\n")
      print("$shuzhu\n")
     print("$stringsba\n")*/
    /*val ifpanduan = lian().ifpanduan()
    print(ifpanduan.toString())*/
   /* val xunhuang = lian().xunhuang()
    print(xunhuang)*/
    /* val leixingpand = lian().leixingpand()
     print(leixingpand)*/
    /*val whenxuanzhe = lian().whenxuanzhe()
    print(whenxuanzhe)*/
   /* val hanshu = lian().hanshu("faas", "fasfd")
    print(hanshu)*/
    /*val kebianchanshu = lian().kebianchanshu("1", "2", "a", "b", "d", "g")
    print(kebianchanshu)*/
    val fanxinghdas = lian().fanxinghdas("a", 1, 6, 0.3, 'a')
    print(fanxinghdas)
}

