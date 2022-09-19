abstract class DigitalPayment(nama: String, saldo: Long) {

    var nama: String = nama
    var saldo: Long = saldo

    fun getnama():String{
        return nama
    }

    fun getSaldo1():Long{
        return saldo
    }

    fun setsaldo(saldo: Long){
        this.saldo = saldo
    }

    abstract fun transfer(dp: DigitalPayment, nominal: Long)

    open fun printBuktiTransfer(penerima: DigitalPayment, nominal: Long) {
        if (penerima is BNImo) {
            print("Transfer ke BNI Mobile")
        } else if (penerima is BRImo) {
            print("Transfer ke BRI Mobile")
        } else if (penerima is Dana) {
            print("Transfer ke DANA")
        } else if (penerima is Ovo) {
            print("Transfer ke OVO")
        }
        println(" atas nama " + penerima.nama + " sebesar Rp " + nominal + " sukses")
    }


}
