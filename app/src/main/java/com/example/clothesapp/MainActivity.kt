package com.example.clothesapp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.clothesapp.data.DataObject
import com.example.clothesapp.data.DataObject.currentListOfBitmaps
import com.example.clothesapp.data.DataObject.currentListOfClothes
import com.example.clothesapp.ktClasses.*
import com.example.clothesapp.ml.Modeltwo
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            var image = data?.data
            try {
                var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, image)
                findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
                val blackAndWhiteBitmap = createBlackAndWhite(bitmap)
//                val reqBuilder =
//                val response = Request.Post("https://api.remove.bg/v1.0/removebg")
//                    .addHeader("X-Api-Key", "INSERT_YOUR_API_KEY_HERE")
//                    .body(
//                        MultipartEntityBuilder.create()
//                            .addBinaryBody("image_file", new File("/path/to/file.jpg"))
//                            .addTextBody("size", "auto")
//                            .build()
//                    ).execute();
//                response.saveContent(new File("no-bg.png"))

                if (blackAndWhiteBitmap != null) {
                    currentListOfBitmaps.add(blackAndWhiteBitmap)
                    currentListOfClothes.add(getClothes(bitmap, blackAndWhiteBitmap))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "image error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.removeItem -> {
                val currentFragment = getVisibleFragment()
//                currentFragment!!.requireView().findNavController().navigate(R.id.action_imagesFragment_to_removeFragment)
                when (DataObject.currentFragment) {
                    R.id.imagesFragment -> {
                        DataObject.currentFragment = R.id.removeFragment
                        currentFragment!!.requireView().findNavController()
                            .navigate(R.id.action_imagesFragment_to_removeFragment)
                    }
                    R.id.changeImageFragment -> {
                        DataObject.currentFragment = R.id.removeFragment
                        currentFragment!!.requireView().findNavController()
                            .navigate(R.id.action_changeImageFragment_to_removeFragment)
                    }
                    R.id.editClothesFragment -> {
                        DataObject.currentFragment = R.id.removeFragment
                        currentFragment!!.requireView().findNavController()
                            .navigate(R.id.action_editClothesFragment_to_removeFragment)
                    }
                    R.id.startFragment -> {
                        DataObject.currentFragment = R.id.removeFragment
                        currentFragment!!.requireView().findNavController()
                            .navigate(R.id.action_startFragment_to_removeFragment)
                    }
//                    NavHostFragment -> Toast.makeText(this, "ШТО? ${currentFragment!!.id}", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.setItem -> {
                val currentFragment = getVisibleFragment()/*
                val uri = Uri.parse("tshirt://tshirt.com/set")
                val intent = Intent()
                intent.setAction(Intent.ACTION_VIEW)
                intent.data = uri
                currentFragment!!.requireView().findNavController().navigate*/
//                currentFragment!!.requireView().findNavController().navigate(R.id.action_imagesFragment_to_removeFragment)
                when (DataObject.currentFragment) {
                    R.id.imagesFragment -> {
                        DataObject.currentFragment = R.id.startFragment
                        currentFragment!!.requireView().findNavController()
                            .navigate(R.id.action_imagesFragment_to_startFragment)
                    }
                    R.id.changeImageFragment -> {
                        DataObject.currentFragment = R.id.startFragment
                        currentFragment!!.requireView().findNavController()
                            .navigate(R.id.action_changeImageFragment_to_startFragment)
                    }
                    R.id.editClothesFragment -> {
                        DataObject.currentFragment = R.id.startFragment
                        currentFragment!!.requireView().findNavController()
                            .navigate(R.id.action_editClothesFragment_to_startFragment)
                    }
                    R.id.removeFragment -> {
                        DataObject.currentFragment = R.id.startFragment
                        currentFragment!!.requireView().findNavController()
                            .navigate(R.id.action_removeFragment_to_startFragment)
                    }
//                    NavHostFragment -> Toast.makeText(this, "ШТО? ${currentFragment!!.id}", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "set", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun startGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }
/*    @Throws(IOException::class)
    fun loadModelFile():MappedByteBuffer{
        val fileDescriptor = this.assets.openFd("model.tflite")
        val fileInputStream = FileInputStream(fileDescriptor.fileDescriptor)
        var fileChannel = fileInputStream.channel
        val startOffSets = fileDescriptor.startOffset
        val declaredLenght = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffSets, declaredLenght              )
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getClothes(bitmap: Bitmap, bwBitmap: Bitmap): Cloth {
        val resized = Bitmap.createScaledBitmap(bitmap, 28, 28, true)
//            val tbuffer = TensorImage.fromBitmap(resized)

        val buffer = resized
        val model = Modeltwo.newInstance(this)

        val array = FloatArray(28 * 28)
        for (i in array.indices) {
//                Log.d("CCC", array[i].toString())
            val color = buffer.getColor(i % 28, i / 28)
            array[i] = (color.blue() + color.red() + color.green()) / 3
            Log.d("CCC", array[i].toString())
        }
        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 28, 28), DataType.FLOAT32)
        inputFeature0.loadArray(array)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        Log.d("DDD", outputFeature0.floatArray.size.toString())
        outputFeature0.floatArray.forEach {
            Log.d("BBB", it.toString())
        }
        // Releases model resources if no longer used.
        model.close()

        if (outputFeature0.floatArray[0] >= outputFeature0.floatArray[1]) {
            return Cloth(CN.TSHIRT, ClothesColor.WHITE, CT.LIGHT_TOP, 0, bitmap)
        } else {
            return Cloth(CN.JEANS, ClothesColor.BLACK, CT.LIGHT_DOWN, 1, bitmap)
        }
    }

    private fun getVisibleFragment(): Fragment? {
        val fragmentManager: FragmentManager = this@MainActivity.supportFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment.isVisible)
                return fragment
        }
        return null
    }

    fun createContrast(src: Bitmap, value: Double): Bitmap? {
        // image size
        val width = src.width
        val height = src.height
        // create output bitmap
        val bmOut = Bitmap.createBitmap(width, height, src.config)
        // color information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int
        // get contrast value
        val contrast = Math.pow((100 + value) / 100, 2.0)

        // scan through all pixels
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get pixel color
                pixel = src.getPixel(x, y)
                A = android.graphics.Color.alpha(pixel)
                // apply filter contrast for every channel R, G, B
                R = android.graphics.Color.red(pixel)
                R = (((R / 255.0 - 0.5) * contrast + 0.5) * 255.0).toInt()
                if (R < 0) {
                    R = 0
                } else if (R > 255) {
                    R = 255
                }
                G = android.graphics.Color.red(pixel)
                G = (((G / 255.0 - 0.5) * contrast + 0.5) * 255.0).toInt()
                if (G < 0) {
                    G = 0
                } else if (G > 255) {
                    G = 255
                }
                B = android.graphics.Color.red(pixel)
                B = (((B / 255.0 - 0.5) * contrast + 0.5) * 255.0).toInt()
                if (B < 0) {
                    B = 0
                } else if (B > 255) {
                    B = 255
                }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, android.graphics.Color.argb(A, R, G, B))
            }
        }
        return bmOut
    }

    fun createBlackAndWhite(src: Bitmap): Bitmap? {
        val width = src.width
        val height = src.height
        val bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val factor = 255f
        val redBri = 0.2126f
        val greenBri = 0.2126f
        val blueBri = 0.0722f
        val length = width * height
        val inpixels = IntArray(length)
        val oupixels = IntArray(length)
        src.getPixels(inpixels, 0, width, 0, 0, width, height)
        var point = 0
        for (pix in inpixels) {
            val R = pix shr 16 and 0xFF
            val G = pix shr 8 and 0xFF
            val B = pix and 0xFF
            val lum = redBri * R / factor + greenBri * G / factor + blueBri * B / factor
            if (lum > 0.4) {
                oupixels[point] = -0x1
            } else {
                oupixels[point] = -0x1000000
            }
            point++
        }
        bmOut.setPixels(oupixels, 0, width, 0, 0, width, height)
        return bmOut
    }
}
