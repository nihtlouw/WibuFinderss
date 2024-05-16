package com.manpro.wibufinders.DummyFiles

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDate
import java.io.Serializable


@Parcelize
data class AnimeFest(
    val error: Boolean,
    val message: String,
    val data: List<AnimeFestEventDetail>
) : Parcelable
@Parcelize
data class AnimeFestEventDetail(
    val id: Int,
    val eventName: String,
    val description: String,
    val eventDate: LocalDate,
    val location: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val sponsorName: String,
    val contactPersonWhatsApp: String,
    val eventForumLink: String

) : Parcelable

val AnimeFestList = AnimeFest(
    error = false,
    message = "Success",
    data = listOf(
        AnimeFestEventDetail(
            id = 1,
            eventName = "Reuni Kebahagiaan Genshin Impact 2024",
            description = "Dear Travelers,\n\nPenantian lama sudah berakhir! Berikut detail acara dari ReuniKebahagiaan 2024. Kalian dapat kesempatan untuk menangin Primogems loh!\n\n〓Online Event Reuni Kebahagiaan 2024〓\n\n【Cara Berpartisipasi】\nTuliskan harapan-harapan kamu kepada Travelers lain selama periode event ini di kolom komentar, dengan hashtag ReuniKebahagiaan dan dapatkan kesempatan untuk menangkan Primogems!",
            eventDate = LocalDate.of(2024, 3, 31),
            location = "Jakarta, Indonesia",
            imageUrl = "https://upload-os-bbs.hoyolab.com/upload/2024/03/21/69fd94a726b1a74c940f3e21fc6e5b09_3514892246968476074.jpg",
            latitude = -6.187976503084043,
            longitude = 106.82418882550553,
            sponsorName = "Hoyoverse",
            contactPersonWhatsApp = "+6281234567890",
            eventForumLink = "https://www.hoyolab.com/article/26203424"
        ),
        AnimeFestEventDetail(
            id = 2,
            eventName = "Coswalk & Anisong Competition Tsukaramen",
            description = "RAF Creative x Tsukaramen present: Coswalk & Anisong Competition Tsukaramen\n26 April 2024\n@ Tsukaramen Bintara, Bekasi\nHTM: Free (silakan jajan saja di sana)\n‼️Dilarang membawa makanan dan minuman dari luar\n.\n.\nPendaftaran lomba: GRATIS\n.\nJuri Coswalk: YANDZI\nJuri Anisong: Togimaru\n.\n.\nInfo:\nhttps://www.instagram.com/rafcreative?igsh=MTU1NDUxZnNueHZmcw==\n.\nhttps://www.instagram.com/tsukaramen.id...\ntsukaramen",
            eventDate = LocalDate.of(2024, 4, 26),
            location = "Tsukaramen Bintara, Bekasi",
            imageUrl = "https://scontent.fcgk42-1.fna.fbcdn.net/v/t39.30808-6/439973397_10225142517738774_3128223686340967712_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_ohc=NRWZO0j91foQ7kNvgE1KfGL&_nc_ht=scontent.fcgk42-1.fna&oh=00_AYAg0E3EULeDe5ae2EUR35bX5UmEBHMXWVVAI7jIkuBqLQ&oe=66483374",
            latitude = -6.23303355849814,
            longitude = 106.96672505221647,
            sponsorName = "RAF Creative",
            contactPersonWhatsApp = "+6281234567890",
            eventForumLink = "https://www.facebook.com/groups/251875943835/permalink/10162451176183836/"
        ),
        AnimeFestEventDetail(
            id = 3,
            eventName = "KOI EXPO 2024 COSWALK & COSPLAY COMPETITION",
            description = "KOI EXPO 2024 COSWALK & COSPLAY COMPETITION\n27 April 2024\n@ Innovation Convention Centre (ICC) Bogor\nSTART : 14:00\nDaftar ulang / OTS : 11:00 - 13:30\nHTM: (??)\n\nYuk para penggemar jejepangan, anime, game cosplay merapat! Di tanggal 27 April ini bakal ada lomba Coswalk & Cosplay di KOI EXPO 2024. Tapi bukan cuma itu doang. Di acara ini juga bakal ada Food Bazaar, Pameran Koi, dan acara-acara menarik lainnya. Pendaftaran lomba Coswalk & Cosplay Competitionnya FREE alias Gratis!!",
            eventDate = LocalDate.of(2024, 4, 27),
            location = "Bogor",
            imageUrl = "https://scontent.fcgk42-1.fna.fbcdn.net/v/t39.30808-6/438869022_10225142669742574_3404271107504981621_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=39eI8oyXU7wQ7kNvgHR8kjE&_nc_ht=scontent.fcgk42-1.fna&oh=00_AYDcmpD8jsV21UMKByMISGcuFHC6GOTVro1hmD8WFgYWqA&oe=66482A36",
            latitude = -6.500589973771029,
            longitude = 106.84518116756163,
            sponsorName = "KOI EXPO",
            contactPersonWhatsApp = "+6283895216748",
            eventForumLink = "https://l.facebook.com/l.php?u=https%3A%2F%2Fwww.instagram.com%2Fp%2FC55FmRTBkMH%2F%3Figsh%3DaW1mOXJ1Y2QwcmRu&h=AT3DvtRXmyn2KryxECebNRdOFhwJtCuhPKlCTeRUUq6xsSd9ivWiZjMP-wB4bFq6Xt0HPTyXybgP9c5tO4avI8wACfh8EeY-nVhhJeO94tcQhkkT-3NMmxm59_SyXlN6laFJFvLueR1NkPY4T1xr&__tn__=-UK-R&c[0]=AT17HpO8c1ofk2_shMM1xRIrkTD493doMJ3lzRKlYN_mg4V9ZONf5sZ_VY2z6B-4mCZqbGxYVAJhz0JVT2rZPOD3KJrZdp7wlynXQpBbUyqz-hwSIK42k1BKH-JlrSCm_e3kGggRsDaIDVV9vlQXVBD_yVJEcDBoZzvoMYWxO_9ydXiZYLLCSfWn6RoDSeG7iPwGOJ4euMY32OM-AFqTp1P1-5AJpKRi2FiWHUMPxijWL-s"
        ),
        AnimeFestEventDetail(
            id = 4,
            eventName = "REJPOP 7 \"The Lost Memory of 90s\"",
            description = "REJPOP 7 \"The Lost Memory of 90s\"\n❤ Coswalk Competition\nRegist : Online Rp.15.000 - OTS Rp. 20.000\nGform: https://forms.gle/DQFkTSC36gZCfbk88\nCoswalk Judges @chelynchan27 @timothyabraham90 Juara 1-3 : @200.000 + Trophy + Certificate Best Challenge : @100.000 + Trophy + Certificate >> Best Costume >> Best Character >> Favourite by Judges >> Best Female >> Best Male\n❤ Sing Cover Competition\nRegist : Solo 35.000 - Duet 50.000\nGform : https://forms.gle/Koko6bNMXm99tRFGA\nSing Cover Judges @buladbarry @galieh_krueger Juara 1 : Rp. 500.000 + Trophy + Certificate Juara 2 : Rp. 300.000 + Trophy + Certificate Juara 3 : Rp. 200.000 + Trophy + Certificate Best Challenge : Rp. 100.000 + Trophy + Certificate Awards: Trophy + Certificate >> Best Stage Act >> Best Female >> Favourite by Judges >> Best High Note >> Best Male REGISTRASI: An/ Linda Rachmawati Mandiri 1560017459811 BCA 8420913312 Dana 085814133311 Ovo 085814133311 Shopeepay 085814133311 Gopay 085814133311",
            eventDate = LocalDate.of(2024, 4, 27),
            location = "Bogor",
            imageUrl = "https://scontent.fcgk42-1.fna.fbcdn.net/v/t39.30808-6/437759076_10225099833911705_4701454015251643145_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_ohc=FhhZPs-Py7oQ7kNvgGRfWVC&_nc_ht=scontent.fcgk42-1.fna&oh=00_AYBl-ghurA2-S6uQNFp-1ZDqzJBJ-b0zENXehX6NMB_PBw&oe=664816A5",
            latitude = -6.604362810102168,
            longitude = 106.7962050540692,
            sponsorName = "REJPOP",
            contactPersonWhatsApp = "+6285814133311",
            eventForumLink = "https://www.facebook.com/groups/251875943835/permalink/10162433012953836/"
        ),
        AnimeFestEventDetail(
            id = 5,
            eventName = "Berwiburia Japan Festival",
            description = "RAF Creative presents: Berwiburia Japan Festival 27 April 2024 @ Transpark Mall Juanda, Bekasi HTM: Free Judge: SORA Info: https://www.instagram.com/rafcreative?igsh=MTU1NDUxZnNueHZmcw== https://www.instagram.com/berwiburia.id... Berwiburia",
            eventDate = LocalDate.of(2024, 4, 27),
            location = "Bekasi",
            imageUrl = "https://scontent.fcgk42-1.fna.fbcdn.net/v/t39.30808-6/439472354_10225142726263987_7314061295246571065_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=5f2048&_nc_ohc=vMXMO7gxdGYQ7kNvgHN5BOG&_nc_ht=scontent.fcgk42-1.fna&oh=00_AYDDeaTUfPuWgcAxkMXtU1KLLpmhi2rInEQgDK-OkOFZkg&oe=66482665",
            latitude = -6.2493418667820215,
            longitude = 107.01677486940744,
            sponsorName = "RAF Creative",
            contactPersonWhatsApp = "+6281234567890",
            eventForumLink = "https://www.facebook.com/groups/251875943835/permalink/10162451278018836/"
        ),
        AnimeFestEventDetail(
            id = 6,
            eventName = "YAMATO DAMASHII XVII",
            description = "YAMATO DAMASHII XVII Eien no Sakura Saki 27-28 April 2024 @ STBA YAPARI - ABA, Bandung 09.00 -20.00 HTM: Presale Rp 25rb HIMADE SEKOLAH TINGGI BAHASA ASING YAPARI MEMPERSEMBAHKAN : YAMATO DAMASHII XVII \"Eien no Sakura Saki - 永遠の桜先\" 📆 Catat tanggal penting nya ya! Pembukaan pendaftaran registrasi lomba akademik dan non-akademik serta Food Bazaar dan Pre-sale Ticketing tiket (Presale seharga 25.000) akan dibuka mulai 27 Januari 2024. Jangan sampai ketinggalan! 🎉 \nYamatoDamashii17 EiennoSakuraSaki HimadeYapari EventYapari EventYamatoDamashii EventYapari BandungEvent Bandung EventAnime EventJapan eventJepang",
            eventDate = LocalDate.of(2024, 4, 27),
            location = "Bandung",
            imageUrl = "https://scontent.fcgk42-1.fna.fbcdn.net/v/t39.30808-6/437607134_10225144056497242_1832754427685544414_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=e7M9jm2UcFQQ7kNvgHgIf77&_nc_ht=scontent.fcgk42-1.fna&oh=00_AYB04HDgIoXHQRSJ0pRVe9o9fpL0DgXFBFDqhSIkIgplJg&oe=664832E0",
            latitude = -6.891879559039053,
            longitude = 107.60438188105952,
            sponsorName = "HIMADE SEKOLAH TINGGI BAHASA ASING YAPARI",
            contactPersonWhatsApp = "+6282118701432",
            eventForumLink = "https://www.facebook.com/groups/251875943835/permalink/10162281324023836/"
        ),
        AnimeFestEventDetail(
            id = 7,
            eventName = "Jakarta Idol Festival ~ 2nd Anniversary Mangdu JPop Zone~",
            description = "Zygma Organizer Proudly Present JAKARTA IDOL FESTIVAL X 2ND ANNIVERSARY MANGDUJPOPZONE 28 April 2024 @ Drop Off Mangga Dua Square HTM: Presale Rp 25rb OTS Rp 30rb Acara: Guest Performance Coswalk Competition (6 pemenang) Bazzar FnB & Merchandise Guest Performance : 1. @shojo_complex 2. @lumina.scarlet 3. @twentynineteens 4. @amaimonogatari_ 5. @nekodachi_ 6. @hira_dazzle 7. @bgr48id 8. @ametta_official 9. @kimiko_ofc 10. @officialeighteen_ 11. @starshineofc_ 12. @officialvaleriee 13. @levia_ofc 14. @zenjaband_id 15. @can_i_rock Host : SASA @ameilianatas Coswalk Judge : ERIC & DESY NARITA @desy_narita",
            eventDate = LocalDate.of(2024, 4, 28),
            location = "Jakarta Utara",
            imageUrl = "https://scontent.fcgk42-1.fna.fbcdn.net/v/t39.30808-6/433304696_10224994852607238_690761227180472366_n.jpg?_nc_cat=109&ccb=1-7&_nc_sid=5f2048&_nc_ohc=4DWAOQvX7PIQ7kNvgEoEeQC&_nc_ht=scontent.fcgk42-1.fna&oh=00_AYBqn3DEtFi3mBWwySgyVnm4HcAGrsXTjPnvaAIpI70Eag&oe=66483F0B",
            latitude = -6.138549396991391,
            longitude = 106.83162694724972,
            sponsorName = "Zygma Organizer",
            contactPersonWhatsApp = "+6281234567890",
            eventForumLink = "https://www.facebook.com/groups/251875943835/permalink/10162280397208836/"
        ),
        AnimeFestEventDetail(
            id = 8,
            eventName = "GLORI JAPANESE FESTIVAL",
            description = "SMP Global Mandiri Jakarta Proudly presents: GLORI JAPANESE FESTIVAL 30 April & 4 Mei 2024 @ Sekolah Global Mandiri Jakarta, Cakung, Jakarta HTM: Early Bird Rp 130rb (s/d 31 Maret 2024) OTS (??) . Early bird tickets: https://artatix.co.id atau bit.ly/tiketjfest GRAB IT FAST!!! . . COMPETITION Selasa, 30 April 2024 - Japanese Cover Dance - E-Sport: Mobile Legend - Japanese Cover Song AWARDING CONCERT & GUEST STAR PERFORMANCE Sabtu, 4 Mei 2024 - Japanese Food Stand - Japanese Culture Stand - Cosplay Anime - Drawing Manga Character Guest Star : JKT 48 @jkt48 Opening by: Zenja Band x Desi Narita Yuriwa . . . Info: https://www.instagram.com/glori_jfest2024... gloriJFest",
            eventDate = LocalDate.of(2024, 4, 30),
            location = "Jakarta Timur",
            imageUrl = "https://scontent.fcgk42-1.fna.fbcdn.net/v/t39.30808-6/437764245_10225104827716547_6233857273976274562_n.jpg?_nc_cat=111&ccb=1-7&_nc_sid=5f2048&_nc_ohc=yOoKNgq1Q28Q7kNvgExIZVi&_nc_ht=scontent.fcgk42-1.fna&oh=00_AYCtET7lajyhhI0TAB1TvOB9QhTvKBeZ99qs2Yb01TJc1A&oe=664811F0",
            latitude = -6.1727989611043705,
            longitude = 106.96278632522912,
            sponsorName = "SMP Global Mandiri Jakarta",
            contactPersonWhatsApp = "+6281234567890",
            eventForumLink = "https://www.facebook.com/groups/251875943835/permalink/10162406921623836/"
        ),
        AnimeFestEventDetail(
            id = 9,
            eventName = "INDIST 2024 COSWALK COMPETITION",
            description = "INDIST 2024 COSWALK COMPETITION 📅 Tanggal : 2 Mei 2024 📍 Lokasi : Balai Pemuda Surabaya 💵HTM : FREE HALOO sobatindist , yuk datang dan daftarkan diri kalian untuk mengikuti lomba coswalk di event: ⚔️👑🏰 INDIST 2024!! ⚔️👑🏰 🏆COSWALK COMPETITION🏆 🎉Judges Coswalk : @Yupi_Kazuki 📝 Link Pendaftaran : https://bit.ly/LOMBACOSWALK-INDIST2024 (atau klik link di bio @indist2024) 💵Biaya Registrasi: Rp. 10.000,- CP : -0882-1716-0889 (Geo) -0822-2977-4640 (Yusril) Medpart : @pan.t.su @daichi.ne @rokyuu.id @sidoarjo_cosplay @kitsu.id . . . Info: https://www.instagram.com/indist2024?igsh=MTAyMjVncHFwc2JjNQ==",
            eventDate = LocalDate.of(2024, 5, 21),
            location = "Surabaya",
            imageUrl = "https://scontent.fcgk42-1.fna.fbcdn.net/v/t39.30808-6/438928563_10225123523223923_3305513343889295325_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=5f2048&_nc_ohc=RFqnc2_gGJAQ7kNvgGyeaFM&_nc_ht=scontent.fcgk42-1.fna&oh=00_AYCd7qNvvnm0VrYRtjzyK8JvXdtlR6RJpWyCJKsnIkUhFw&oe=66481094",
            latitude = -7.263852880899626,
            longitude = 112.74529333873485,
            sponsorName = "INDIST",
            contactPersonWhatsApp = "+6281234567890",
            eventForumLink = "https://www.indist2024.com"
        ),
        AnimeFestEventDetail(
            id = 10,
            eventName = "Anime Festival Asia 2024 AFAID 24",
            description = "\"Anime Festival Asia 2024\" AFAID24 AFAKembali Hello again, Indonesia! We are finally back! (Tunggu update info selanjutnya) 📅: 3-5 Mei 2024 📍: Jakarta Convention Center Link postingan: https://www.instagram.com/reel/C0D8DQVrVnm/?igshid=MzRlODBiNWFlZA== https://fb.watch/oxHB_P2KYS/?mibextid=Nif5oz Social media: https://www.facebook.com/animefestivalasia https://instagram.com/animefestivalasia",
            eventDate = LocalDate.of(2024, 5, 20),
            location = "Jakarta Pusat",
            imageUrl = "https://scontent.fcgk42-1.fna.fbcdn.net/v/t39.30808-6/435899190_1869450713505136_2220550794989233484_n.jpg?_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_ohc=qDGwndASgTQQ7kNvgHWBe1t&_nc_ht=scontent.fcgk42-1.fna&oh=00_AYBu7Te76RCZQXKV0Qlg-TL4qmAZl7vBdRHHW-tLhb7cTQ&oe=66482BF8",
            latitude = -6.214084912069854,
            longitude = 106.80721622522951,
            sponsorName = "Anime Festival Asia",
            contactPersonWhatsApp = "+6281234567890",
            eventForumLink = "https://www.facebook.com/groups/251875943835/permalink/10162220884583836/"
        )
    )
)