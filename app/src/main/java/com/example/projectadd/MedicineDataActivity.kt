package com.example.projectadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MedicineDataActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_data)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView) // Initialize RecyclerView

        val arrContact = ArrayList<ContactModel>()
        arrContact.add(ContactModel("abhayarishta", 0, "1"))
        arrContact.add(ContactModel("amritarishta", 0, "2"))
        arrContact.add(ContactModel("aragvadharishta", 0, "3"))
        arrContact.add(ContactModel("aravindasava", 0, "4"))
        arrContact.add(ContactModel("arjunarishta/parthadyarishta", 0, "5"))
        arrContact.add(ContactModel("ashokarishta", 0, "6"))
        arrContact.add(ContactModel("ashvagandharishta", 0, "7"))
        arrContact.add(ContactModel("balarishta", 0, "8"))
        arrContact.add(ContactModel("chandanasava", 0, "9"))
        arrContact.add(ContactModel("dashamularishta", 0, "10"))
        arrContact.add(ContactModel("drakshasava", 0, "11"))
        arrContact.add(ContactModel("draksharishta", 0, "12"))
        arrContact.add(ContactModel("jirakadyarishta", 0, "13"))
        arrContact.add(ContactModel("kanakasava", 0, "14"))
        arrContact.add(ContactModel("kumaryasava", 0, "15"))
        arrContact.add(ContactModel("kutajarishta", 0, "16"))
        arrContact.add(ContactModel("khadirarishta", 0, "17"))
        arrContact.add(ContactModel("lodhrasava/rodhrasava", 0, "18"))
        arrContact.add(ContactModel("lohasava", 0, "19"))
        arrContact.add(ContactModel("mustakarishta", 0, "20"))
        arrContact.add(ContactModel("pippalyadyasava", 0, "21"))
        arrContact.add(ContactModel("rohitakarishta", 0, "22"))
        arrContact.add(ContactModel("sarasvatarishta", 0, "23"))
        arrContact.add(ContactModel("ushirasava", 0, "24"))
        arrContact.add(ContactModel("vasakasava", 0, "25"))
        arrContact.add(ContactModel("punarnavasava", 0, "26"))
        arrContact.add(ContactModel("arka yavani/arka ajvayana", 0, "27"))
        arrContact.add(ContactModel("arka shatpushpa/mishr eyarka", 0, "28"))
        arrContact.add(ContactModel("arka pudina", 0, "29"))
        arrContact.add(ContactModel("agastya haritaki/agastya rasayana", 0, "30"))
        arrContact.add(ContactModel("bilvadi leha", 0, "31"))
        arrContact.add(ContactModel("brahama rasayana", 0, "32"))
        arrContact.add(ContactModel("chitraka haritaki", 0, "33"))
        arrContact.add(ContactModel("chyavanprash avaleha", 0, "34"))
        arrContact.add(ContactModel("drakshavaleha", 0, "35"))
        arrContact.add(ContactModel("haridrakhanda paka", 0, "36"))
        arrContact.add(ContactModel("kutajavaleha", 0, "37"))
        arrContact.add(ContactModel("kalyanak guda", 0, "38"))
        arrContact.add(ContactModel("kantakaryavaleha", 0, "39"))
        arrContact.add(ContactModel("kushmandaka rasayana", 0, "40"))
        arrContact.add(ContactModel("puga khanda/supari paka", 0, "41"))
        arrContact.add(ContactModel("saubhagyashunthi paka", 0, "42"))
        arrContact.add(ContactModel("shatavariguda", 0, "43"))
        arrContact.add(ContactModel("vasavaleha", 0, "44"))
        arrContact.add(ContactModel("vyaghri haritaki", 0, "45"))
        arrContact.add(ContactModel("dashamula kvatha churna", 0, "46"))
        arrContact.add(ContactModel("dashamula katutraya kvatha churna", 0, "47"))
        arrContact.add(ContactModel("dhanyapanchaka kashaya churna", 0, "48"))
        arrContact.add(ContactModel("guduchyadi kashaya churna", 0, "49"))
        arrContact.add(ContactModel("indukantam kashayam churna", 0, "50"))
        arrContact.add(ContactModel("pancha-valkala kashaya churna", 0, "51"))
        arrContact.add(ContactModel("pathyadi kvatha(shadanga) churna", 0, "52"))
        arrContact.add(ContactModel("phalatrikadi kvatha churna", 0, "53"))
        arrContact.add(ContactModel("rasnasaptaka kashaya churna", 0, "54"))
        arrContact.add(ContactModel("shadanga kvatha churna", 0, "55"))
        arrContact.add(ContactModel("trinapanchamula kvatha churna", 0, "56"))
        arrContact.add(ContactModel("varunadi kvatha churna", 0, "57"))
        arrContact.add(ContactModel("vasaguduchyadi kashaya churna", 0, "58"))
        arrContact.add(ContactModel("amritadi guggulu", 0, "59"))
        arrContact.add(ContactModel("gokshuradi guggulu", 0, "60"))
        arrContact.add(ContactModel("kanchanara guggulu", 0, "61"))
        arrContact.add(ContactModel("kaishora guggulu", 0, "62"))
        arrContact.add(ContactModel("lakshadi guggulu", 0, "63"))
        arrContact.add(ContactModel("navak guggulu", 0, "64"))
        arrContact.add(ContactModel("rasna guggulu", 0, "65"))
        arrContact.add(ContactModel("simhanada guggulu", 0, "66"))
        arrContact.add(ContactModel("saptavinshati guggulu", 0, "67"))
        arrContact.add(ContactModel("triphala guggulu", 0, "68"))
        arrContact.add(ContactModel("trayodashanga guggulu", 0, "69"))
        arrContact.add(ContactModel("punarnava guggulu", 0, "70"))
        arrContact.add(ContactModel("yogaraja guggulu", 0, "71"))
        arrContact.add(ContactModel("brahmi ghrita", 0, "72"))
        arrContact.add(ContactModel("dadimadi ghrita", 0, "73"))
        arrContact.add(ContactModel("guggulutiktaka ghrita", 0, "74"))
        arrContact.add(ContactModel("jathyadi ghrita", 0, "75"))
        arrContact.add(ContactModel("kalyanaka ghrita", 0, "76"))
        arrContact.add(ContactModel("kshirashatpala ghrita", 0, "77"))
        arrContact.add(ContactModel("mahatiktaka ghrita", 0, "78"))
        arrContact.add(ContactModel("mahatriphala ghrita", 0, "79"))
        arrContact.add(ContactModel("pippalyadi ghrita", 0, "80"))
        arrContact.add(ContactModel("sarivadyasava", 0, "81"))
        arrContact.add(ContactModel("phalasarpis", 0, "82"))
        arrContact.add(ContactModel("jatyadi taila", 0, "83"))
        arrContact.add(ContactModel("nimb taila", 0, "84"))
        arrContact.add(ContactModel("pinda taila", 0, "85"))
        arrContact.add(ContactModel("chandana bala lakshadi taila", 0, "86"))
        arrContact.add(ContactModel("balashwagandhadi taila", 0, "87"))
        arrContact.add(ContactModel("dhanvantara taila", 0, "88"))
        arrContact.add(ContactModel("guluchyadi taila", 0, "89"))
        arrContact.add(ContactModel("kottamchukkadi taila", 0, "90"))
        arrContact.add(ContactModel("ksheerabala taila", 0, "91"))
        arrContact.add(ContactModel("mahamasha taila", 0, "92"))
        arrContact.add(ContactModel("mahanarayana taila", 0, "93"))
        arrContact.add(ContactModel("murivenna", 0, "94"))
        arrContact.add(ContactModel("neelibringadi taila", 0, "95"))
        arrContact.add(ContactModel("prasarinyadi taila", 0, "96"))
        arrContact.add(ContactModel("sahacharadi taila", 0, "97"))
        arrContact.add(ContactModel("sahacharadi kuzhampu", 0, "98"))
        arrContact.add(ContactModel("trayodashanga taila", 0, "99"))
        arrContact.add(ContactModel("ashwagandha churna", 0, "100"))
        arrContact.add(ContactModel("bala churna", 0, "101"))
        arrContact.add(ContactModel("chitrakadi vati", 0, "102"))
        arrContact.add(ContactModel("dashamula rasayana", 0, "103"))
        arrContact.add(ContactModel("drakshadi churna", 0, "104"))
        arrContact.add(ContactModel("gandharvahastadi kashaya churna", 0, "105"))
        arrContact.add(ContactModel("gandharvahastadi kvatha churna", 0, "106"))
        arrContact.add(ContactModel("hingvastaka churna", 0, "107"))
        arrContact.add(ContactModel("indukantam ghrita", 0, "108"))
        arrContact.add(ContactModel("kanchanara guggulu", 0, "109"))
        arrContact.add(ContactModel("karpuradi churna", 0, "110"))
        arrContact.add(ContactModel("krimikuthara rasa", 0, "111"))
        arrContact.add(ContactModel("laghusutshekhar ras", 0, "112"))
        arrContact.add(ContactModel("lavanabhaskar churna", 0, "113"))
        arrContact.add(ContactModel("madiphala rasayana", 0, "114"))
        arrContact.add(ContactModel("mahadhanvantara ghrita", 0, "115"))
        arrContact.add(ContactModel("maharasnadi kwath churna", 0, "116"))
        arrContact.add(ContactModel("panchamrut parpati", 0, "117"))
        arrContact.add(ContactModel("pippalimuladi churna", 0, "118"))
        arrContact.add(ContactModel("punarnavadi kwath churna", 0, "119"))
        arrContact.add(ContactModel("rasnadi kwath churna", 0, "120"))
        arrContact.add(ContactModel("shadbindu taila", 0, "121"))
        arrContact.add(ContactModel("shwaskuthara ras", 0, "122"))
        arrContact.add(ContactModel("suvarnabhasma", 0, "123"))
        arrContact.add(ContactModel("sutshekhar ras", 0, "124"))
        arrContact.add(ContactModel("trikatu churna", 0, "125"))
        arrContact.add(ContactModel("triphala churna", 0, "126"))
        arrContact.add(ContactModel("vasavaleha", 0, "127"))
        arrContact.add(ContactModel("vidangadi churna", 0, "128"))
        arrContact.add(ContactModel("abhrak bhasma", 0, "129"))
        arrContact.add(ContactModel("akik bhasma", 0, "130"))
        arrContact.add(ContactModel("arjuna pishti", 0, "131"))
        arrContact.add(ContactModel("chandanasava", 0, "132"))
        arrContact.add(ContactModel("chitrakadi vati", 0, "133"))
        arrContact.add(ContactModel("garbhapal ras", 0, "134"))
        arrContact.add(ContactModel("gokshuradi guggulu", 0, "135"))
        arrContact.add(ContactModel("godant bhasma", 0, "136"))
        arrContact.add(ContactModel("godhruti bhasma", 0, "137"))
        arrContact.add(ContactModel("jaharmohra pishti", 0, "138"))
        arrContact.add(ContactModel("kanchnar guggulu", 0, "139"))
        arrContact.add(ContactModel("kamdudha ras", 0, "140"))
        arrContact.add(ContactModel("kapardak bhasma", 0, "141"))
        arrContact.add(ContactModel("kasis bhasma", 0, "142"))
        arrContact.add(ContactModel("khatti shrikhand", 0, "143"))
        arrContact.add(ContactModel("kumari asava", 0, "144"))
        arrContact.add(ContactModel("leela vilas ras", 0, "145"))
        arrContact.add(ContactModel("mahalaxmi vilas ras", 0, "146"))
        arrContact.add(ContactModel("makardhwaj bhasma", 0, "147"))
        arrContact.add(ContactModel("mukta pishti", 0, "148"))
        arrContact.add(ContactModel("mukta sukti pishti", 0, "149"))
        arrContact.add(ContactModel("prabhakar vati", 0, "150"))
        arrContact.add(ContactModel("praval pishti", 0, "151"))
        arrContact.add(ContactModel("purna chandrodaya ras", 0, "152"))
        arrContact.add(ContactModel("rajah pravartini vati", 0, "153"))
        arrContact.add(ContactModel("rajah pravartini vati", 0, "154"))
        arrContact.add(ContactModel("saptamrit loha", 0, "155"))
        arrContact.add(ContactModel("shankh bhasma", 0, "156"))
        arrContact.add(ContactModel("shringa bhasma", 0, "157"))
        arrContact.add(ContactModel("sutshekhar ras", 0, "158"))
        arrContact.add(ContactModel("swarna bhasma", 0, "159"))
        arrContact.add(ContactModel("swarna makshik bhasma", 0, "160"))
        arrContact.add(ContactModel("tapyadi loha", 0, "161"))
        arrContact.add(ContactModel("trailokya chintamani ras", 0, "162"))
        arrContact.add(ContactModel("trivikram ras", 0, "163"))
        arrContact.add(ContactModel("vajarasanadi kashaya", 0, "164"))
        arrContact.add(ContactModel("vajra dhattu bhasma", 0, "165"))
        arrContact.add(ContactModel("yakuti ras", 0, "166"))
        arrContact.add(ContactModel("yograj guggulu", 0, "167"))
        arrContact.add(ContactModel("akik pishti", 0, "168"))
        arrContact.add(ContactModel("heerak bhasma", 0, "169"))
        arrContact.add(ContactModel("sahastravedhi", 0, "170"))
        arrContact.add(ContactModel("akhrot pishti", 0, "171"))
        arrContact.add(ContactModel("aneerikadi pishti", 0, "172"))
        arrContact.add(ContactModel("chandrakala ras", 0, "173"))
        arrContact.add(ContactModel("chaturbhuj ras", 0, "174"))
        arrContact.add(ContactModel("garbhpal ras", 0, "175"))
        arrContact.add(ContactModel("gudantikaran ras", 0, "176"))
        arrContact.add(ContactModel("kanchnar guggulu", 0, "177"))
        arrContact.add(ContactModel("kamdudha ras", 0, "178"))
        arrContact.add(ContactModel("kasisadi taila", 0, "179"))
        arrContact.add(ContactModel("komal singhara pishti", 0, "180"))
        arrContact.add(ContactModel("mahalaxmi vilas ras", 0, "181"))
        arrContact.add(ContactModel("makshik bhasma", 0, "182"))
        arrContact.add(ContactModel("mukta pishti", 0, "183"))
        arrContact.add(ContactModel("mukta yukta pishti", 0, "184"))
        arrContact.add(ContactModel("praval pishti", 0, "185"))
        arrContact.add(ContactModel("rajah pravartani vati", 0, "186"))
        arrContact.add(ContactModel("ras sindur", 0, "187"))
        arrContact.add(ContactModel("shankh bhasma", 0, "188"))
        arrContact.add(ContactModel("shatavari guggulu", 0, "189"))
        arrContact.add(ContactModel("shatavari pishti", 0, "190"))
        arrContact.add(ContactModel("shring bhasma", 0, "191"))
        arrContact.add(ContactModel("sutshekhar ras", 0, "192"))
        arrContact.add(ContactModel("trailokya chintamani ras", 0, "193"))
        arrContact.add(ContactModel("trivang bhasma", 0, "194"))
        arrContact.add(ContactModel("trivikram ras", 0, "195"))
        arrContact.add(ContactModel("vajarasanadi kashay", 0, "196"))
        arrContact.add(ContactModel("vatarakta chintamani ras", 0, "197"))
        arrContact.add(ContactModel("vrihat singhara pishti", 0, "198"))
        arrContact.add(ContactModel("akik bhasma", 0, "199"))
        arrContact.add(ContactModel("amalaki rasayan", 0, "200"))
        arrContact.add(ContactModel("brahmi vati", 0, "201"))
        arrContact.add(ContactModel("chandra prabha vati", 0, "202"))
        arrContact.add(ContactModel("chandraprabha vati", 0, "203"))
        arrContact.add(ContactModel("chitrakadi vati", 0, "204"))
        arrContact.add(ContactModel("godanti bhasma", 0, "205"))
        arrContact.add(ContactModel("godhant bhasma", 0, "206"))
        arrContact.add(ContactModel("kamadudha ras", 0, "207"))
        arrContact.add(ContactModel("kanchnar guggulu", 0, "208"))
        arrContact.add(ContactModel("khadiradi vati", 0, "209"))
        arrContact.add(ContactModel("laghu shankha vati", 0, "210"))
        arrContact.add(ContactModel("mahashankha vati", 0, "211"))
        arrContact.add(ContactModel("praval panchamrit", 0, "212"))
        arrContact.add(ContactModel("shankh bhasma", 0, "213"))
        arrContact.add(ContactModel("sutashekhar ras", 0, "214"))
        arrContact.add(ContactModel("trikatu churna", 0, "215"))
        arrContact.add(ContactModel("triphala guggulu", 0, "216"))
        arrContact.add(ContactModel("triphala churna", 0, "217"))
        arrContact.add(ContactModel("vajra dhatu bhasma", 0, "218"))
        arrContact.add(ContactModel("yakrit pliha ras", 0, "219"))
        arrContact.add(ContactModel("amritarishta", 0, "220"))
        arrContact.add(ContactModel("aravindasava", 0, "221"))
        arrContact.add(ContactModel("ashwagandharishta", 0, "222"))
        arrContact.add(ContactModel("balarishta", 0, "223"))
        arrContact.add(ContactModel("chandanasava", 0, "224"))
        arrContact.add(ContactModel("dashamularishta", 0, "225"))
        arrContact.add(ContactModel("draksharishta", 0, "226"))
        arrContact.add(ContactModel("haridrakhanda", 0, "227"))
        arrContact.add(ContactModel("kumaryasava", 0, "228"))
        arrContact.add(ContactModel("lodhrasava", 0, "229"))
        arrContact.add(ContactModel("madhusnuhi rasayana", 0, "230"))
        arrContact.add(ContactModel("maha-manjishtadi kwath", 0, "231"))
        arrContact.add(ContactModel("maha-manjishtadi kshir", 0, "232"))
        arrContact.add(ContactModel("pippalyasava", 0, "233"))
        arrContact.add(ContactModel("punarnavasava", 0, "234"))
        arrContact.add(ContactModel("rasnadi kwath", 0, "235"))
        arrContact.add(ContactModel("sarivadyasava", 0, "236"))
        arrContact.add(ContactModel("vasakasava", 0, "237"))
        arrContact.add(ContactModel("vidangasava", 0, "238"))
        arrContact.add(ContactModel("abhrak bhasma", 0, "239"))
        arrContact.add(ContactModel("akik bhasma", 0, "240"))
        arrContact.add(ContactModel("chandanasava", 0, "241"))
        arrContact.add(ContactModel("chitrakadi vati", 0, "242"))
        arrContact.add(ContactModel("garbhapal ras", 0, "243"))
        arrContact.add(ContactModel("gokshuradi guggulu", 0, "244"))
        arrContact.add(ContactModel("godant bhasma", 0, "245"))
        arrContact.add(ContactModel("godhruti bhasma", 0, "246"))
        arrContact.add(ContactModel("jaharmohra pishti", 0, "247"))
        arrContact.add(ContactModel("kanchnar guggulu", 0, "248"))
        arrContact.add(ContactModel("kamdudha ras", 0, "249"))
        arrContact.add(ContactModel("kapardak bhasma", 0, "250"))
        arrContact.add(ContactModel("kasis bhasma", 0, "251"))
        arrContact.add(ContactModel("khatti shrikhand", 0, "252"))
        arrContact.add(ContactModel("kumari asava", 0, "253"))
        arrContact.add(ContactModel("leela vilas ras", 0, "254"))
        arrContact.add(ContactModel("mahalaxmi vilas ras", 0, "255"))
        arrContact.add(ContactModel("makardhwaj bhasma", 0, "256"))
        arrContact.add(ContactModel("mukta pishti", 0, "257"))
        arrContact.add(ContactModel("mukta sukti pishti", 0, "258"))
        arrContact.add(ContactModel("prabhakar vati", 0, "259"))
        arrContact.add(ContactModel("praval pishti", 0, "260"))
        arrContact.add(ContactModel("purna chandrodaya ras", 0, "261"))
        arrContact.add(ContactModel("rajah pravartini vati", 0, "262"))
        arrContact.add(ContactModel("rajah pravartini vati", 0, "263"))
        arrContact.add(ContactModel("saptamrit loha", 0, "264"))
        arrContact.add(ContactModel("shankh bhasma", 0, "265"))
        arrContact.add(ContactModel("shringa bhasma", 0, "266"))
        arrContact.add(ContactModel("sutshekhar ras", 0, "267"))
        arrContact.add(ContactModel("swarna bhasma", 0, "268"))
        arrContact.add(ContactModel("swarna makshik bhasma", 0, "269"))
        arrContact.add(ContactModel("tapyadi loha", 0, "270"))
        arrContact.add(ContactModel("trailokya chintamani ras", 0, "271"))
        arrContact.add(ContactModel("trivikram ras", 0, "272"))
        arrContact.add(ContactModel("vajarasanadi kashaya", 0, "273"))
        arrContact.add(ContactModel("vajra dhattu bhasma", 0, "274"))
        arrContact.add(ContactModel("yakuti ras", 0, "275"))
        arrContact.add(ContactModel("yograj guggulu", 0, "276"))
        arrContact.add(ContactModel("akik pishti", 0, "277"))
        arrContact.add(ContactModel("heerak bhasma", 0, "278"))
        arrContact.add(ContactModel("sahastravedhi", 0, "279"))
        arrContact.add(ContactModel("akhrot pishti", 0, "280"))
        arrContact.add(ContactModel("aneerikadi pishti", 0, "281"))
        arrContact.add(ContactModel("chandrakala ras", 0, "282"))
        arrContact.add(ContactModel("chaturbhuj ras", 0, "283"))
        arrContact.add(ContactModel("garbhpal ras", 0, "284"))
        arrContact.add(ContactModel("gudantikaran ras", 0, "285"))
        arrContact.add(ContactModel("kanchnar guggulu", 0, "286"))
        arrContact.add(ContactModel("kamdudha ras", 0, "287"))
        arrContact.add(ContactModel("kasisadi taila", 0, "288"))
        arrContact.add(ContactModel("komal singhara pishti", 0, "289"))
        arrContact.add(ContactModel("mahalaxmi vilas ras", 0, "290"))
        arrContact.add(ContactModel("makshik bhasma", 0, "291"))
        arrContact.add(ContactModel("mukta pishti", 0, "292"))
        arrContact.add(ContactModel("mukta yukta pishti", 0, "293"))
        arrContact.add(ContactModel("praval pishti", 0, "294"))
        arrContact.add(ContactModel("rajah pravartani vati", 0, "295"))
        arrContact.add(ContactModel("ras sindur", 0, "296"))
        arrContact.add(ContactModel("shankh bhasma", 0, "297"))
        arrContact.add(ContactModel("shatavari guggulu", 0, "298"))
        arrContact.add(ContactModel("shatavari pishti", 0, "299"))
        arrContact.add(ContactModel("shring bhasma", 0, "300"))
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Set layout manager
        val recyclerAdapter = RecyclerContactAdapter(this, arrContact, ::incrementQuantity, ::decrementQuantity)
        recyclerView.adapter = recyclerAdapter

        // Upload data to Firestore
        uploadDataToFirestore(arrContact)
    }
    private fun uploadDataToFirestore(arrContact: ArrayList<ContactModel>) {
        val collectionRef = firestore.collection("medalist")

        for (contact in arrContact) {
            val document = HashMap<String, Any>()
            document["name"] = contact.name
            document["quantity"] = contact.quantity

            collectionRef.add(document)
                .addOnSuccessListener { documentReference ->
                    println("DocumentSnapshot added with ID: ${documentReference.id}")
                    // Update the contact model with the Firestore-generated ID
                    contact.id = documentReference.id // Assuming id field is a String
                }
                .addOnFailureListener { e ->
                    println("Error adding document: $e")
                }
        }
    }

    private fun updateQuantityInFirestore(itemId: String, newQuantity: Int) {
        val docRef = firestore.collection("updatedQuantities").document(itemId)

        docRef.set(mapOf("quantity" to newQuantity))
            .addOnSuccessListener {
                println("Quantity updated successfully")
            }
            .addOnFailureListener { exception ->
                println("Error updating quantity: $exception")
            }
    }

    private fun incrementQuantity(itemId: String, currentQuantity: Int) {
    val newQuantity = currentQuantity + 1
    updateQuantityInFirestore(itemId, newQuantity)
}

// Decrement quantity when decrementButton is clicked
private fun decrementQuantity(itemId: String, currentQuantity: Int) {
    if (currentQuantity > 0) {
        val newQuantity = currentQuantity - 1
        updateQuantityInFirestore(itemId, newQuantity)
    }
}
}
