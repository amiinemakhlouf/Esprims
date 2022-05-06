package esprims.gi2.esprims.model

data class Payement(
    val id:Int?=null,
    val userId:String?=null,
    val premiereTranche:Boolean?=null,
    val deuxiemeTranche:Boolean?=null,
    val troisiemeTranche:Boolean?=null
)
