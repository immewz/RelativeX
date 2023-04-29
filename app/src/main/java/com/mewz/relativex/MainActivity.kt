package com.mewz.relativex

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mewz.relativex.databinding.ActivityMainBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.toObservable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Just
        Observable.just(getNumberFromOneToTen())
            .subscribe {
                Log.d("just",it.toString())
            }

        // fromIterable
        Observable.fromIterable(getNumberFromOneToTen())
            .subscribe{
                Log.d("iterable",it.toString())
            }

        // fromCallable
        Observable.fromCallable { getNumberFromOneToTen() }
            .subscribe {
                Log.d("callable",it.toString())
            }

        // Rxkotlin => same Iterable
        getNumberFromOneToTen().toObservable()
            .subscribe{
                Log.d("toObservable",it.toString())
            }

        // Filter
        getNumberFromOneToTen().toObservable()
            .filter{it % 2 == 0}
            .subscribe {
                Log.d("filter",it.toString())
            }

        // Skip
        getNumberFromOneToTen().toObservable()
            .skip(4)
            .subscribe {
                Log.d("skip",it.toString())
            }

        // Take
        getNumberFromOneToTen().toObservable()
            .take(4)
            .subscribe {
                Log.d("take",it.toString())
            }

        // Distinct Until Changed
        getDuplicateNumberWithOneToTen().toObservable()
            .distinctUntilChanged()
            .subscribe {
                Log.d("distinctUntilChanged",it.toString())
            }

        // Transforming Operators
        // Map
        getNumberFromOneToTen().toObservable()
            .map { it % 2 }
            .subscribe {
                Log.d("map",it.toString())
            }

        // Operator That Produce a Single Result
        // Any
        getNumberFromOneToTen().toObservable()
            .any{it == 5}
            .toObservable()
            .subscribe {
                Log.d("any",it.toString())
            }

        // Reduce
        getNumberFromOneToTen().toObservable()
            .reduce { first, second -> first + second  }
            .subscribe {
                Log.d("reduce",it.toString())
            }


        // Combing Operator
        // CombineLatest
        io.reactivex.rxjava3.core.Observable.combineLatest(
            listOf(
                getNumberFromOneToTen().toObservable(),
                getHundred().toObservable()
            )
        ){
            it.map { it.toString() }.joinToString(",")
        }.subscribe {
            Log.d("combineLatest",it.toString())
        }

        // Zip
        io.reactivex.rxjava3.core.Observable.zip(
            listOf(
                getNumberFromOneToTen().toObservable(),
                getHundred().toObservable()
            )
        ){
            it.map { it.toString() }.joinToString(",")
        }.subscribe {
            Log.d("zip",it.toString())
        }
   }

    private fun getNumberFromOneToTen(): List<Int>{
        return (1..10).toList()
    }

    private fun getHundred(): List<Int>{
        return arrayListOf(100,200,300,400,500,600,700,800,900,1000)
    }

    private fun getDuplicateNumberWithOneToTen(): List<Int>{
        return arrayListOf(1,2,2,3,4,4,5,6,7,8,8,9,10)
    }
}