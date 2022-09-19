open class Dana(nama:String, saldo :Long, noHp:String):MobileWallet(nama, saldo, noHp) {
    var danaFeeTransfer:Long = 1000
    override fun transfer(dp: DigitalPayment, nominal: Long) {
        this.setfeetransferbank(this.danaFeeTransfer)
        if (dp is Ovo){
            println("Transfer gagal akun dana tidak valid")
        }
        else{
            super.transfer(dp, nominal)
        }

    }
}