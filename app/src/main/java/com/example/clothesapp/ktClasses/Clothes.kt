package com.example.clothesapp.ktClasses

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

enum class CN(val clothName: String) {
    PULLOVER("Свитер"),
    JEANS("Джинсы"),
    COAT("Куртка"),
    TSHIRT("Футболка"),
    SHIRT("Кофта"),
    SHORTS("Шорты")
}

enum class CT(val typeName: String) {
    WARM_TOP("Тёплый верх"),
    WARM_DOWN("Тёплый низ"),
    HOT_TOP("Очень тёплый верх"),
    HOT_DOWN("Очень тёплый низ"),
    LIGHT_TOP("Лёгкий верх"),
    LIGHT_DOWN("Лёгкий низ")
}

class Cloth(val cloth: CN, val clothColor: ClothesColor, val type: CT, val id: Int, val photo: Bitmap)

class Clothes(var name: ClothName, var photo: Bitmap, var clothColor: ClothesColor) {
    var clothName = name.name
    var clothType = name.type
}

open class TypeCloth(var name: String) {
    var typeName = name
}// тип одежды верх/низ

open class ClothColor(name: String) {
    var colorName = name
}// цвет одежды

open class ClothName(var type: CT, var name: String, var warmth: Int) {
}// название одежды


// классы Type
class Top : TypeCloth("Верх")

class Bottom : TypeCloth("Низ")

class Hats : TypeCloth("Головной убор")

// классы Color
@RequiresApi(Build.VERSION_CODES.O)
enum class ClothesColor(val colorToName: Pair<Color, String>) {
    WHITE(Pair(Color.valueOf(0F, 0F, 0F), "Белый")),
    BLACK(Pair(Color.valueOf(255F, 255F, 255F), "Чёрный")),
    RED(Pair(Color.valueOf(255F, 0F, 0F), "Красный")),
    GREEN(Pair(Color.valueOf(0F, 255F, 0F), "Зелёный")),
    BLUE(Pair(Color.valueOf(0F, 0F, 255F), "Синий")),
    YELLOW(Pair(Color.valueOf(255F, 255F, 0F), "Жёлтый")),
}

//class White : ClothColor("Белый")
//
//class Black : ClothColor("Чёрный")
//
//class Red : ClothColor("Красный")
//
//class Blue : ClothColor("Синий")
//
//class Yellow : ClothColor("Жёлтый")
//
//class Green : ClothColor("Зелёный")
//
//class Purple : ClothColor("Фиолетовый")
//
//class Orange : ClothColor("Оранжевый")
//
//class Gray : ClothColor("Серый")
//
//class Pink : ClothColor("Розовый")


// классы Name

//верх
class TShirt : ClothName(CT.LIGHT_TOP, "Футболка", 2)

class Shirt : ClothName(CT.WARM_TOP, "Кофта", 5)

class Jacket : ClothName(CT.HOT_TOP, "Куртка", 8)

//низ
class Jeans : ClothName(CT.WARM_DOWN, "Джинсы", 5)

class Shorts : ClothName(CT.LIGHT_DOWN, "Шорты", 2)

//головной убор
//class Beanie : ClothName(Hats(), "Шапка", 8)
//
//class Cylinder : ClothName(Hats(), "Цилиндр", 5)