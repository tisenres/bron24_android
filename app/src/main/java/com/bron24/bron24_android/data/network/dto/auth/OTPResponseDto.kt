import com.google.gson.annotations.SerializedName

data class PhoneNumberResponseDto(
    @SerializedName("result") val result: String
)

data class OTPCodeResponseDto(
    @SerializedName("result") val status: String
)