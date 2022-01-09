package com.example.clothesapp.ktClasses

import android.graphics.Color
import java.lang.Math.sqrt

class ClothesMaster {
    fun choose(clothes: MutableList<Cloth>, temperature: Int): MutableList<MutableList<Cloth>> {
        val weatherClothes = weatherChoosing(clothes, temperature).first
        println("$weatherClothes weatherClothes")
        val case = weatherChoosing(clothes, temperature).second
        println("$case case")
        return styleChoosing(weatherClothes, case)
    }


    fun weatherChoosing(
        clothes: MutableList<Cloth>,
        temperature: Int
    ): Pair<MutableList<Cloth>, Int> {
        val weatherClothes = mutableListOf<Cloth>()
        val line = findLine(temperature)

        println("$line line")

        var case: Int = 0
        line.let {
            when {
                it >= 7 -> {
                    case = 1
                }
                it == 6 -> {
                    case = 2
                }

                it < 3 -> {
                    case = 3
                }

                else -> {
                    case = 4
                }
            }
        }

        when (case) {
            1 -> {
                for (cloth in clothes) {
                    if (cloth.type == CT.LIGHT_TOP || cloth.type == CT.LIGHT_DOWN) {
                        weatherClothes.add(cloth)
                    }
                }
            }
            2 -> {
                for (cloth in clothes) {
                    if (cloth.type == CT.LIGHT_TOP || cloth.type == CT.LIGHT_DOWN || cloth.type == CT.WARM_TOP) {
                        weatherClothes.add(cloth)
                    }
                }
            }
            3 -> {
                for (cloth in clothes) {
                    if (cloth.type == CT.WARM_DOWN || cloth.type == CT.WARM_TOP || cloth.type == CT.HOT_TOP || cloth.type == CT.LIGHT_DOWN) {
                        weatherClothes.add(cloth)
                    }
                }
            }
            4 -> {
                for (cloth in clothes) {
                    if (cloth.type == CT.LIGHT_TOP || cloth.type == CT.HOT_TOP || cloth.type == CT.WARM_DOWN || cloth.type == CT.WARM_TOP) {
                        weatherClothes.add(cloth)
                    }
                }
            }
        }
        return Pair(weatherClothes, case)
    }


    fun styleChoosing(clothes: MutableList<Cloth>, case: Int): MutableList<MutableList<Cloth>> {
        val styledClothes = mutableListOf<MutableList<Cloth>>()
        val clothMap = clothSort(clothes)

        when (case) {
            1 -> {
                for (topCloth in clothMap[CT.LIGHT_TOP]!!) {
                    for (downCloth in clothMap[CT.LIGHT_DOWN]!!) {
                        if (topCloth.cloth == CN.SHIRT && downCloth.cloth == CN.SHORTS) {
                            break
                        } else {
                            if (areSuitable(topCloth.clothColor, downCloth.clothColor)) {
                                styledClothes.add(mutableListOf(topCloth, downCloth))
                            }
                        }
                    }
                }
                if (styledClothes.isNotEmpty()) {
                    return styledClothes
                }
            }
            2 -> {
                for (warmTopCloth in clothMap[CT.WARM_TOP]!!) {
                    for (lightTopCloth in clothMap[CT.LIGHT_TOP]!!) {
                        for (lightDownCloth in clothMap[CT.LIGHT_DOWN]!!) {
                            if (areSuitable(
                                    warmTopCloth.clothColor,
                                    lightDownCloth.clothColor
                                ) && areSuitable(
                                    lightTopCloth.clothColor, lightDownCloth.clothColor
                                )
                            ) {
                                styledClothes.add(
                                    mutableListOf(
                                        warmTopCloth,
                                        lightTopCloth,
                                        lightDownCloth
                                    )
                                )
                            }
                        }
                    }
                }
                if (styledClothes.isNotEmpty()) {
                    return styledClothes
                }
            }
            3 -> {
                val downClothes = mutableListOf<Cloth>()
                clothMap[CT.LIGHT_DOWN]!!.forEach {
                    downClothes.add(it)
                }
                clothMap[CT.WARM_DOWN]!!.forEach {
                    downClothes.add(it)
                }
                for (hotTopCloth in clothMap[CT.HOT_TOP]!!) {
                    for (lightTopCloth in clothMap[CT.LIGHT_TOP]!!) {
                        for (downCloth in downClothes) {
                            if (areSuitable(lightTopCloth.clothColor, downCloth.clothColor)) {
                                styledClothes.add(
                                    mutableListOf(
                                        hotTopCloth,
                                        lightTopCloth,
                                        downCloth
                                    )
                                )
                            }
                        }
                    }
                }
                if (styledClothes.isNotEmpty()) {
                    return styledClothes
                }
            }
            4 -> {
                for (hotTopCloth in clothMap[CT.HOT_TOP]!!) {
                    for (warmTopCloth in clothMap[CT.WARM_TOP]!!) {
                        for (lightTopCloth in clothMap[CT.LIGHT_TOP]!!) {
                            for (warmDownCloth in clothMap[CT.WARM_DOWN]!!) {
                                if (areSuitable(
                                        warmTopCloth.clothColor,
                                        warmDownCloth.clothColor
                                    ) && areSuitable(
                                        lightTopCloth.clothColor, warmDownCloth.clothColor
                                    )
                                ) {
                                    styledClothes.add(
                                        mutableListOf(
                                            hotTopCloth,
                                            warmTopCloth,
                                            lightTopCloth,
                                            warmDownCloth
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                if (styledClothes.isNotEmpty()) {
                    return styledClothes
                }
            }
        }
        return styledClothes
    }

    fun areSuitable(color1: ClothesColor, color2: ClothesColor): Boolean {
        val length = kotlin.math.sqrt(
            ((color1.colorToName.first.red() - color2.colorToName.first.red()) * (color1.colorToName.first.red() - color2.colorToName.first.red()) + (color1.colorToName.first.green() - color2.colorToName.first.green()) * (color1.colorToName.first.green() - color2.colorToName.first.green()) + (
                    color1.colorToName.first.blue() - color2.colorToName.first.blue()) * (color1.colorToName.first.blue() - color2.colorToName.first.blue())).toFloat()
        )
        return length <= 150
    }


    fun findLine(temperature: Int): Int {
        when {
            temperature < -40 -> {
                return 1
            }
            temperature < -20 && temperature >= -30 -> {
                return 2
            }
            temperature < -20 && temperature >= -30 -> {
                return 3
            }
            temperature < -5 && temperature >= -20 -> {
                return 4
            }
            temperature < 5 && temperature >= -5 -> {
                return 5
            }
            temperature in 5..14 -> {
                return 6
            }
            temperature in 15..19 -> {
                return 7
            }
            temperature in 20..29 -> {
                return 8
            }
            temperature in 30..39 -> {
                return 9
            }
            temperature >= 40 -> {
                return 10
            }
            else -> {
                return 0
            }
        }
    }


    fun clothSort(clothes: MutableList<Cloth>): MutableMap<CT, MutableList<Cloth>> {
        val clothMap = mutableMapOf<CT, MutableList<Cloth>>(
            CT.LIGHT_TOP to mutableListOf<Cloth>(),
            CT.LIGHT_DOWN to mutableListOf<Cloth>(),
            CT.WARM_TOP to mutableListOf<Cloth>(),
            CT.WARM_DOWN to mutableListOf<Cloth>(),
            CT.HOT_TOP to mutableListOf<Cloth>()
        )
        for (cloth in clothes) {
            when (cloth.type) {
                CT.LIGHT_TOP -> {
                    clothMap[CT.LIGHT_TOP]!!.add(cloth)
                }
                CT.LIGHT_DOWN -> {
                    clothMap[CT.LIGHT_DOWN]!!.add(cloth)
                }
                CT.WARM_TOP -> {
                    clothMap[CT.WARM_TOP]!!.add(cloth)
                }
                CT.WARM_DOWN -> {
                    clothMap[CT.WARM_DOWN]!!.add(cloth)
                }
                CT.HOT_TOP -> {
                    clothMap[CT.HOT_TOP]!!.add(cloth)
                }
            }
        }
        return clothMap
    }
}