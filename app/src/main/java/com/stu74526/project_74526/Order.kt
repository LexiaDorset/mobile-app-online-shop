package com.stu74526.project_74526

import java.util.Date

class Order(
    var total: Double, var products: MutableMap<String, Int>,
    var date: com.google.firebase.Timestamp
) {
}