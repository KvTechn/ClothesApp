package com.example.clothesapp.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.clothesapp.*
import com.example.clothesapp.ktClasses.*

object DataObject {
    val allNames = mutableListOf<ClothName>(
        TShirt(), Shirt(), Jacket(), Jeans(), Shorts()
    )

    var currentFragment = R.id.imagesFragment//надеюсь, что найду фикс поумнее

    val clothes = arrayOf(
        "Футболка",
        "Кофта",
        "Куртка",
        "Джинсы",
        "Шорты",
    )

    val allColors = arrayOf(
        "Белый",
        "Чёрный",
        "Красный",
        "Синий",
        "Жёлтый",
        "Зелёный"
    )

    val allTypes = arrayOf(
        "Верх",
        "Низ",
        "Головной убор"
    )

    var nameToClothName = mapOf<String, CN>(
        "Футболка" to CN.TSHIRT,
        "Кофта" to CN.SHIRT,
        "Куртка" to CN.COAT,
        "Джинсы" to CN.JEANS,
        "Шорты" to CN.SHORTS,
//        "Шапка" to Beanie(),
//        "Цилиндр" to Cylinder()
    )

    val CNtoCT = mapOf<CN, CT>(
        CN.TSHIRT to CT.LIGHT_TOP,
        CN.SHORTS to CT.LIGHT_DOWN,
        CN.JEANS to CT.WARM_DOWN,
        CN.SHIRT to CT.WARM_TOP,
        CN.PULLOVER to CT.WARM_TOP,
        CN.COAT to CT.HOT_TOP
    )

    var toChoose = arrayOf(
        "Одежду",
        "Цвет одежды"
    )

    @RequiresApi(Build.VERSION_CODES.O)
    var colorNameToColor = mapOf<String, ClothesColor>(
        "Белый" to ClothesColor.WHITE,
        "Чёрный" to ClothesColor.BLACK,
        "Красный" to ClothesColor.RED,
        "Синий" to ClothesColor.BLUE,
        "Жёлтый" to ClothesColor.YELLOW,
        "Зелёный" to ClothesColor.GREEN,
//        "Фиолетовый" to ClothesColor.,
//        "Оранжевый" to Orange(),
//        "Серый" to Gray(),
//        "Розовый" to Pink()
    )



    var latlan: Location = Location("")

    var chosenItems = mutableListOf<Int>()

    var currentListOfBitmaps = mutableListOf<Bitmap>()
    var currentListOfClothes = mutableListOf<Cloth>()
}