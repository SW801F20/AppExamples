package com.example.sensorx.Data

//en private construcctor er en måde at gøre det til en singleton
class FakeDatabase private constructor() {

    var sensorValueDAO = FakeSensorValueDAO()
        private set

    companion object {
        //@Volatile = writes to this property is immediately visible to other threads
        // det her er en instance af typen FakeDatabse, den kan være null, det er det '?' betyder.
        // instances bliver også sat til null som default det er hvad '= null' gør.
        @Volatile private var instance: FakeDatabase? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: FakeDatabase().also { instance = it }
            }
    }
}