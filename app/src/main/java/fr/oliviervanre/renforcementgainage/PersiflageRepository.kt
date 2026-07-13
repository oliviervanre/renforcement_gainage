package fr.oliviervanre.renforcementgainage

import android.util.Base64
import kotlin.random.Random

object PersiflageRepository {

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
        "TCdleGVyY2ljZSBlc3QgcGFzc8OpLCBub24gdmFpbmN1LiBOdWFuY2UgY3J1ZWxsZS4="
    )

    fun randomRemark(): String {
        val encoded = encodedRemarks[Random.nextInt(encodedRemarks.size)]
        return String(Base64.decode(encoded, Base64.DEFAULT), Charsets.UTF_8)
    }
}
