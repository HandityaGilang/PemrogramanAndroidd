open class Ovo(nama:String, saldo :Long, noHp:String):MobileWallet(nama, saldo, noHp) {
    var ovoFeeTransfer : Long = 2000
    override fun transfer(dp: DigitalPayment, nominal: Long) {
        this.setfeetransferbank(this.ovoFeeTransfer)
        if (dp is Dana){
            println("Transfer gagal akun dana tidak valid")
        }
        else{
            super.transfer(dp, nominal)
        }

    }
}