open class MoblieBanking(nama: String, saldo: Long, noRekening : String) : DigitalPayment(nama, saldo) {

    var checkFee : Boolean = false
    var feeAntarBank : Long = 6000
    var noRekeningg : String = noRekening

    fun isCheckFee(): Boolean{
        return this.checkFee
    }

    override fun transfer(dp: DigitalPayment, nominal: Long) {
        if (nominal<0){
            println("Transfer gagal Input tidak valid")
        }
        if (this.saldo<nominal){
            println("Transfer gagal saldo tidak mencukupi")
        }
        else if (this.isCheckFee()){
            this.setsaldo(getSaldo1()-(nominal+feeAntarBank))
            dp.setsaldo(getSaldo1()+nominal)
            this.printBuktiTransfer(dp,nominal)
        }
    }

    fun setCheckFee1(checkFee : Boolean){
        this.checkFee = checkFee
    }

}