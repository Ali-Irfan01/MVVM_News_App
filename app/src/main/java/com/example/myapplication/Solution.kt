package com.example.myapplication
//
//import android.util.Log
//
///**
// * Example:
// * var li = ListNode(5)
// * var v = li.`val`
// * Definition for singly-linked list.*/
//
//  class ListNode(var `val`: Int) {
//      var next: ListNode? = null
//  }
//class Solution {
//        fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
//            var size1 = 0
//            var size2 = 0
//            var temp1: ListNode? = l1
//            var temp2: ListNode? = l2
//
//            while (temp1 != null) {
//                size1++
//                temp1 = temp1.next
//            }
//            while (temp2 != null) {
//                size2++
//                temp2 = temp2.next
//            }
//
//            val bigger = if (size1 > size2) size1 else size2
//            var holder: Int = 0
//            var result: ListNode? = null
//
//
//            for (i in 0 until bigger) {
//                if (temp1 == null && temp2 != null) {
//                    result!!.`val` = temp2.`val`
//
//                    result = result.next
//                    temp2 = temp2.next
//                }
//                if (temp2 == null && temp1 != null) {
//                    result?.`val` = temp1.`val`
//
//                    result = result?.next
//                    temp1 = temp1.next
//                }
//                if (temp1 != null && temp2 != null) {
//                    if (temp1.`val` + temp2.`val` + holder > 9) {
//                        result?.`val` = ((temp1.`val` + temp2.`val`) % 10) + holder
//                        holder = (temp1.`val` + temp2.`val`) / 10
//
//                        result = result?.next
//                        temp1 = temp1.next
//                        temp2 = temp2.next
//                    } else {
//                        result?.`val` = temp1.`val` + temp2.`val` + holder
//
//                        result = result?.next
//                        temp1 = temp1.next
//                        temp2 = temp2.next
//                    }
//                }
//            }
//            return result
//        }
//}
//fun main()  {
//    var l1: ListNode? = ListNode(2)
//    l1?.next = ListNode(4)
//    l1?.next = ListNode(3)
//
//    var l2: ListNode? = null
//    l2?.`val` = 5
//    l2?.next = ListNode(6)
//    l2?.next = ListNode(4)
//
//    if (l1 != null) {
//        Log.d("TAG" ,"${l1.`val`}")
//    }
//
//    var res = Solution().addTwoNumbers(l1, l2)
//
//    while (res != null) {
//        Log.d("TAG" ,"${res.`val`}")
//        res = res.next
//    }
//}
//
