open class MobileWallet(nama : String, saldo : Long, noHp:String) : DigitalPayment(nama, saldo) {
    var noHp :String = noHp
    var feeTransferBank : Long = 0

    fun setfeetransferbank(feeTransferBank:Long){
        this.feeTransferBank=feeTransferBank
    }
    fun  getnohp(): String {
        return this.noHp
    }

    override fun transfer(dp: DigitalPayment, nominal: Long) {
        if (nominal<0){
            println("Transfer gagal Input tidak valid")
        }
        if (this.getSaldo1()<nominal){
            println("Transfer gagal saldo tidak mencukupi")
        }
        else if (dp is BNImo || dp is BRImo){
            this.setsaldo(getSaldo1()-(nominal+feeTransferBank))
            dp.setsaldo(getSaldo1()+nominal)
            this.printBuktiTransfer(dp,nominal)
        }
        else{
            this.setsaldo(getSaldo1()-nominal)
            dp.setsaldo(getSaldo1()+nominal)
            this.printBuktiTransfer(dp,nominal)
        }
    }

}