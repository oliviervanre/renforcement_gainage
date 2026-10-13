package fr.oliviervanre.renforcementgainage

import android.util.Base64
import kotlin.random.Random

object PersiflageRepository {

    private const val REMARK_CHANCE_DIVISOR = 3

    private val encodedRemarks = listOf(
        "RmVpZ25hc3NlIGRlIHBhbHRvcXVldCwgbm91cyBwYXNzZXJvbnMgZG9uYyBjZXQgZXhlcmNpY2Uu",
        "Q2hlciBhbWksIHZvdHJlIHrDqGxlIGNvbm5hw650IHVuZSDDqWNsaXBzZSByZWdyZXR0YWJsZS4=",
        "Vm9pbMOgIHVuZSByZXRyYWl0ZSB0YWN0aXF1ZSBxdWUgU2FpbnQtU2ltb24gZcO7dCBub3TDqWUgZCd1bmUgcGx1bWUgYWPDqXLDqWUu",
        "Vm90cmUgcHJ1ZGVuY2Ugdm91cyBob25vcmUsIHF1b2lxdWUgdm90cmUgaGFsdMOocmUgdm91cyBtw6lwcmlzZS4=",
        "TGEgcGFyZXNzZSBhIHNlcyByYWlzb25zIHF1ZSBMYSBSb2NoZWZvdWNhdWxkIGF1cmFpdCBmb3J0IGJpZW4gY29tcHJpc2VzLg==",
        "RnV5b25zIGRvbmMgY2V0IGV4ZXJjaWNlLCBwdWlzcXVlIGxlIG1hdMOpcmllbCBtYW5xdWUgZXQgcXVlIGwnaG9ubmV1ciBkZW1ldXJlLg==",
        "RW5jb3JlIHVuZSB2aWN0b2lyZSBkZSBsJ2VzcHJpdCBzdXIgbCfDqWxhc3RpcXVlIGludHJvdXZhYmxlLg==",
        "TGUgcm93aW5nIGF0dGVuZHJhIDsgbnVsIGJlc29pbiBkZSBzaW5nZXIgSGVyY3VsZSBzYW5zIGluc3RydW1lbnQu",
        "RMOpdmVsb3Bww6kgw6lwYXVsZXMgYWpvdXJuw6kgOiBsYSBjb3VyIGVuIHNlcmEgaW5mb3Jtw6llLg==",
        "Q2Ugc2F1dCBkJ2V4ZXJjaWNlIHNlbnQgc29uIENoYW1mb3J0LCB1biBwZXUgc2EgcGFyZXNzZSwgZXQgYmVhdWNvdXAgc29uIGJvbiBzZW5zLg==",
        "UG9pbnQgZGUgbWF0w6lyaWVsLCBwb2ludCBkJ2jDqXJvw69zbWUgaW51dGlsZS4gUGFzc29ucy4=",
        "Vm91cyBlc3F1aXZleiBhdmVjIHVuZSBkaWduaXTDqSBwcmVzcXVlIGFkbWluaXN0cmF0aXZlLg==",
        "TGUgcGFsdG9xdWV0IHN1cnZpdCA7IGwnw6lwYXVsZSBhdXNzaSwgY2UgcXVpIG4nZXN0IHBhcyByaWVuLg==",
        "Vm90cmUgZ3JhbmRldXIgc2UgbWFuaWZlc3RlIGF1am91cmQnaHVpIGRhbnMgbCdhcnQgc3VidGlsIGRlIGwnYWJzdGVudGlvbi4=",
        "UGFzc29ucywgY2hlciBtb25zaWV1ciA6IGlsIGVzdCBkZXMgcmVub25jZW1lbnRzIHF1aSBzYXV2ZW50IGxlcyBhcnRpY3VsYXRpb25zLg==",
        "TGUgY291cmFnZSBlc3Qgc2F1ZiA7IHNldWxzIGxlcyBoYWx0w6hyZXMgc29udCBhYnNlbnRzLg==",
        "Vm90cmUgcHJ1ZGVuY2UgYSBwYXJsw6kuIEVsbGUgYXZhaXQgdW4gYWNjZW50IGRlIHBhbnRvdWZsZS4=",
        "T24gYXBwZWxsZXJhIGNlbGEgZGlzY2VybmVtZW50LCBwb3VyIG3DqW5hZ2VyIHZvdHJlIGFtb3VyLXByb3ByZS4=",
        "Vm9pbMOgIHF1aSBuJ2Fqb3V0ZXJhIHJpZW4gw6Agdm90cmUgbMOpZ2VuZGUsIG1haXMgcHLDqXNlcnZlcmEgdm9zIHRlbmRvbnMu",
        "TCdleGVyY2ljZSBlc3QgcGFzc8OpLCBub24gdmFpbmN1LiBOdWFuY2UgY3J1ZWxsZS4=",
        "TGEgRnJhbmNlIGEgcGV1ciA7IHZvcyBxdWFkcmljZXBzLCBldXgsIHNlbWJsZW50IG7DqWdvY2llci4=",
        "R3JhdmUgbW9tZW50IGRhbnMgY2V0dGUgc8OpYW5jZSA6IGwnZXhlcmNpY2UgZXN0IHN1c3BlbmR1LCBsZSB0YXBpcyByZXRpZW50IHNvbiBzb3VmZmxlLg==",
        "SWwgZXN0IHRlbXBzIGRlIGxlIGRpcmUgYXZlYyBjYWxtZSA6IGxlIG1vdXZlbWVudCBuZSBzZSBmZXJhIHBhcy4=",
        "Q2Ugc29pciwgY2hhY3VuIG1lc3VyZSBsYSBmcmFnaWxpdMOpIGRlIGwnZWZmb3J0IHZvbG9udGFpcmUuIE5vdXMgcGFzc29ucy4=",
        "TGEgc2l0dWF0aW9uIGVzdCBwcsOpb2NjdXBhbnRlLCBtYWlzIHZvcyBhcnRpY3VsYXRpb25zIHJlbWVyY2llbnQgbGEgcsOpZGFjdGlvbi4=",
        "TGUgcGF5cyBzYXVyYSByZXN0ZXIgZGlnbmUgZGV2YW50IGNlIHJlbm9uY2VtZW50IG11c2N1bGFpcmUu",
        "w4lkaXRpb24gc3DDqWNpYWxlIDogbCdleGVyY2ljZSByZWN1bGUsIGwnaG9ubmV1ciBkZW1ldXJlIGVuIGRpcmVjdC4=",
        "TGUgY2FsbWUgZG9pdCBwcsOpdmFsb2lyLiBOb3VzIHNhdXRvbnMgY2V0dGUgw6l0YXBlLg=="
    )

    fun randomRemark(): String {
        val encoded = encodedRemarks[Random.nextInt(encodedRemarks.size)]
        return decode(encoded)
    }

    fun randomRemarkOrNull(): String? {
        if (Random.nextInt(REMARK_CHANCE_DIVISOR) != 0) return null
        return randomRemark()
    }

    private fun decode(encoded: String): String {
        return String(Base64.decode(encoded, Base64.DEFAULT), Charsets.UTF_8)
    }
}
