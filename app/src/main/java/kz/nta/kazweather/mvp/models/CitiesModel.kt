package kz.nta.kazweather.mvp.models


data class CitiesModel(
		val predictions: List<Prediction>,
		val status: String //OK
)

data class Prediction(
		val description: String, //Almaty, Almaty Province, Kazakhstan
		val id: String, //d6782297c5543054165f16a6095bec2524b2490f
		val matched_substrings: List<MatchedSubstring>,
		val place_id: String, //ChIJq8vFFn1ugzgRdm2YrY9mRD0
		val reference: String, //CkQ7AAAA8GLrlBoxD5Lrx7SfGDtVcVQ9d_7_GBcjfcUkWhBjCh0Vlnhjyz0owDM_gt1dvhR81e7se13ITXmB2AbKen6rOhIQI2ah5krLqR_HsL8ddin6fRoUeeAZxrqhUvIkNLKY1qBNTd3OdpA
		val structured_formatting: StructuredFormatting,
		val terms: List<Term>,
		val types: List<String>
)

data class MatchedSubstring(
		val length: Int, //6
		val offset: Int //0
)

data class Term(
		val offset: Int, //0
		val value: String //Almaty
)

data class StructuredFormatting(
		val main_text: String, //Almaty
		val main_text_matched_substrings: List<MainTextMatchedSubstring>,
		val secondary_text: String //Almaty Province Kazakhstan
)

data class MainTextMatchedSubstring(
		val length: Int, //6
		val offset: Int //0
)