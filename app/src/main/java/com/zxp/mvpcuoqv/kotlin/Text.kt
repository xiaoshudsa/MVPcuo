package com.zxp.mvpcuoqv.kotlin


import com.umeng.commonsdk.proguard.y
import com.zxp.mvpcuoqv.Application1907
import com.zxp.mvpcuoqv.kotlin.Ban.Nei
import com.zxp.mvpcuoqv.kotlin.Sealed as Sealed1


open class TextClass() {
    fun da() {
        val context = Application1907.getContext()
    }
}


class Text(name: String, age: Int) : TextClass() {
   @JvmField var user = name
    var nian = age
    var sea = ""

    constructor(name: String, age: Int, sex: String) : this(name, age) {
        sea = sex
    }

   fun get(): String = user + nian + sea
}

open class Ban() {
    companion object {
      @JvmStatic  fun fang(a: String): String {
            /* zhi().nihs("shi")*/
            return "你好"
        }
    }

    open fun gwr(): String {
        return "年时"
    }

    open fun get(): String {
        return "get"
    }

    inner class Nei() {
        fun hao(): String {
            return fang("") + 1
        }
    }
}

interface Infoer {
    fun get(): String {
        return ""
    }

}

class zhi() : Ban(), Infoer {
    fun nihs(a: String): String {
        zhi.Ji().shida("a", "v")
        return "${Ban.fang("")}$a${Nei().hao()}"
    }

    override fun get(): String {
        super<Ban>.get()
        super<Infoer>.get()
        return super<Ban>.get()
    }

    override fun gwr(): String {
        return super.gwr() + ","
    }

    inner class Neiasd() {
        fun gwr(): String {
            return super<Ban>@zhi.get()
        }
    }

    class Ji() : hsi {
        override fun asd() {
            TODO("Not yet implemented")
        }

        override fun shida(a: String, b: String): String {
            return super.shida(a, b)
        }

        override fun cou() {
            TODO("Not yet implemented")
        }
    }

    var a = 1
    fun FeiKong() {
        val s = a as String?
        val s1 = a as? String
    }
}

fun main() {
    /*   var result= Text("aa",123,"ssss")
       print(result.get())*/
//print(Ban.fang(""))
/*    print(zhi().nihs("i"))
    print(zhi().gwr())*/
    print(zhi().Neiasd().gwr())
}