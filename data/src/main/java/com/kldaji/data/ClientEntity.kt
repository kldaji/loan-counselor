package com.kldaji.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kldaji.domain.Gender
import com.kldaji.domain.Loan

@Entity(tableName = "client")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val phone: String,
    @ColumnInfo
    val birth: String,
    @ColumnInfo
    val loan: Loan,
    @ColumnInfo
    val gender: Gender,
    @ColumnInfo
    val meeting: Long,
    @ColumnInfo
    val meetingTime: String,
    @ColumnInfo
    val run: Long,
    @ColumnInfo
    val remark: String,
    @ColumnInfo
    val pictures: List<String>
)
