open class BNImo(nama:String, saldo : Long, noRekening : String) : MoblieBanking(nama,saldo,noRekening) {
    override fun transfer(dp: DigitalPayment, nominal: Long) {
        if (dp is BRImo){
        super.setCheckFee1(true)
            super.transfer(dp, nominal)
        }
        else{
            super.transfer(dp, nominal)
        }
    }
}