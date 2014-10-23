
        //var nswBounds = new google.maps.LatLngBounds(new google.maps.LatLng(-36, 143), new google.maps.LatLng( -29,  152));

        var shareViaTwitterLink  = "https://twitter.com/intent/tweet?original_referer=https%3A%2F%2Fmaps.planningportal.nsw.gov.au&text=The NSW Government has made certain planning maps available in a Google viewer.&tw_p=tweetbutton&url=https%3A%2F%2Fmaps.planningportal.nsw.gov.au&via=NSWPlanning";
        
        var shareViaGooglePlusLink = "https://plusone.google.com/_/+1/confirm?hl=en&url=https%3A%2F%2Fmaps.planningportal.nsw.gov.au";

        var shareViaFacebookLink  = "http://www.facebook.com/share.php?u=https%3A%2F%2Fmaps.planningportal.nsw.gov.au";

        var shareViaEmailLink = "mailto:?subject=Planning%20Viewer&body=The NSW Government has made certain planning maps available in a Google viewer.%0D%0A%0D%0AClick on this link to see the Planning Viewer - https://maps.planningportal.nsw.gov.au/";
        
        var defaultVisibleLayers = [];


        parcelParameter = '';
        zoomLevelParameter = '';
        lgaParameter = '';
        layersParameter = '';

        var lgas = new Array();
        
            
        // lgas.push( {
            // Id: 14, Name: "ALBURY", AbsCode: "50", Centroid: new google.maps.Point("-36.0149001199309","146.956430060807"), 
            // extents: { Bottom: "-36.1159681987399", Top: "-35.9138320411219", Left: "146.812893568015", Right: "147.099966553599"}});
        
            
        // lgas.push( {
            // Id: 6, Name: "ARMIDALE DUMARESQ", AbsCode: "110", Centroid: new google.maps.Point("-30.5813241827594","151.874852224312"), 
            // extents: { Bottom: "-30.9310882221266", Top: "-30.2315601433921", Left: "151.321626088882", Right: "152.428078359741"}});
        
            
        // lgas.push( {
            // Id: 130, Name: "ASHFIELD", AbsCode: "150", Centroid: new google.maps.Point("-33.8886087421105","151.131426111278"), 
            // extents: { Bottom: "-33.9075531766587", Top: "-33.8696643075623", Left: "151.112328751234", Right: "151.150523471321"}});
        
            
        // lgas.push( {
            // Id: 146, Name: "AUBURN", AbsCode: "200", Centroid: new google.maps.Point("-33.8543326168245","151.047989033324"), 
            // extents: { Bottom: "-33.8882813730867", Top: "-33.8203838605623", Left: "151.011856521607", Right: "151.08412154504"}});
        
            
        // lgas.push( {
            // Id: 5, Name: "BALLINA", AbsCode: "250", Centroid: new google.maps.Point("-28.8527031714704","153.485664633205"), 
            // extents: { Bottom: "-29.001098616399", Top: "-28.7043077265418", Left: "153.362494880671", Right: "153.60883438574"}});
        
            
        // lgas.push( {
            // Id: 75, Name: "BALRANALD", AbsCode: "300", Centroid: new google.maps.Point("-34.0440617479445","143.571467448849"), 
            // extents: { Bottom: "-34.9724158352217", Top: "-33.1157076606673", Left: "142.453733700168", Right: "144.68920119753"}});
        
            
        // lgas.push( {
            // Id: 3, Name: "BANKSTOWN", AbsCode: "350", Centroid: new google.maps.Point("-33.9238627345058","151.020806678939"), 
            // extents: { Bottom: "-33.983739206203", Top: "-33.8639862628086", Left: "150.966086189813", Right: "151.075527168066"}});
        
            
        // lgas.push( {
            // Id: 151, Name: "BATHURST REGIONAL", AbsCode: "470", Centroid: new google.maps.Point("-33.473120874642","149.519582468219"), 
            // extents: { Bottom: "-33.9976260871604", Top: "-32.9486156621236", Left: "149.117225504473", Right: "149.921939431966"}});
        
            
        // lgas.push( {
            // Id: 44, Name: "BEGA VALLEY", AbsCode: "550", Centroid: new google.maps.Point("-36.8164666596604","149.720275245812"), 
            // extents: { Bottom: "-37.5050765706739", Top: "-36.1278567486469", Left: "149.356393341656", Right: "150.084157149968"}});
        
            
        // lgas.push( {
            // Id: 87, Name: "BELLINGEN", AbsCode: "600", Centroid: new google.maps.Point("-30.3739484713869","152.72514413452"), 
            // extents: { Bottom: "-30.5670962402989", Top: "-30.1808007024748", Left: "152.389645161936", Right: "153.060643107105"}});
        
            
        // lgas.push( {
            // Id: 138, Name: "BERRIGAN", AbsCode: "650", Centroid: new google.maps.Point("-35.751835746867","145.669841766573"), 
            // extents: { Bottom: "-35.9916766848105", Top: "-35.5119948089235", Left: "145.332039185643", Right: "146.007644347504"}});
        
            
        // lgas.push( {
            // Id: 88, Name: "BLACKTOWN", AbsCode: "750", Centroid: new google.maps.Point("-33.740203947751","150.862473246106"), 
            // extents: { Bottom: "-33.8340601328146", Top: "-33.6463477626875", Left: "150.759629931776", Right: "150.965316560436"}});
        
            
        // lgas.push( {
            // Id: 67, Name: "BLAND", AbsCode: "800", Centroid: new google.maps.Point("-33.9190745171337","146.927172141722"), 
            // extents: { Bottom: "-34.31724920165", Top: "-33.5208998326174", Left: "146.070300020459", Right: "147.784044262985"}});
        
            
        // lgas.push( {
            // Id: 85, Name: "BLAYNEY", AbsCode: "850", Centroid: new google.maps.Point("-33.6165347536364","149.130501615704"), 
            // extents: { Bottom: "-33.8123854179283", Top: "-33.4206840893446", Left: "148.838945474908", Right: "149.422057756499"}});
        
            
        // lgas.push( {
            // Id: 142, Name: "BLUE MOUNTAINS", AbsCode: "900", Centroid: new google.maps.Point("-33.631983666338","150.412932765705"), 
            // extents: { Bottom: "-33.9005892400402", Top: "-33.3633780926358", Left: "150.166029820723", Right: "150.659835710688"}});
        
            
        // lgas.push( {
            // Id: 43, Name: "BOGAN", AbsCode: "950", Centroid: new google.maps.Point("-31.5702499969702","146.906164216954"), 
            // extents: { Bottom: "-32.4474790743063", Top: "-30.693020919634", Left: "146.285102478727", Right: "147.527225955182"}});
        
            
        // lgas.push( {
            // Id: 133, Name: "BOMBALA", AbsCode: "1000", Centroid: new google.maps.Point("-36.9111085644444","148.952763362802"), 
            // extents: { Bottom: "-37.2630691687931", Top: "-36.5591479600958", Left: "148.395279453504", Right: "149.5102472721"}});
        
            
        // lgas.push( {
            // Id: 111, Name: "BOOROWA", AbsCode: "1050", Centroid: new google.maps.Point("-34.2879001950848","148.842366600204"), 
            // extents: { Bottom: "-34.6516400067151", Top: "-33.9241603834546", Left: "148.553622087863", Right: "149.131111112545"}});
        
            
        // lgas.push( {
            // Id: 129, Name: "BOTANY BAY", AbsCode: "1100", Centroid: new google.maps.Point("-33.9510991147962","151.198717434668"), 
            // extents: { Bottom: "-33.9829322866703", Top: "-33.9192659429221", Left: "151.165795224407", Right: "151.231639644929"}});
        
            
        // lgas.push( {
            // Id: 118, Name: "BOURKE", AbsCode: "1150", Centroid: new google.maps.Point("-29.9957771901351","145.36156234889"), 
            // extents: { Bottom: "-30.9930711035785", Top: "-28.9984832766917", Left: "144.065413710257", Right: "146.657710987523"}});
        
            
        // lgas.push( {
            // Id: 125, Name: "BREWARRINA", AbsCode: "1200", Centroid: new google.maps.Point("-29.9503776937028","147.147601316995"), 
            // extents: { Bottom: "-30.901974040458", Top: "-28.9987813469477", Left: "146.349712379952", Right: "147.945490254038"}});
        
            
        // lgas.push( {
            // Id: 57, Name: "BROKEN HILL", AbsCode: "1250", Centroid: new google.maps.Point("-31.9514674043878","141.4787900092"), 
            // extents: { Bottom: "-32.0191461913233", Top: "-31.8837886174524", Left: "141.392821575066", Right: "141.564758443334"}});
        
            
        // lgas.push( {
            // Id: 127, Name: "BURWOOD", AbsCode: "1300", Centroid: new google.maps.Point("-33.8853767343777","151.104844240453"), 
            // extents: { Bottom: "-33.9036517030593", Top: "-33.8671017656961", Left: "151.089010150022", Right: "151.120678330884"}});
        
            
        // lgas.push( {
            // Id: 106, Name: "BYRON", AbsCode: "1350", Centroid: new google.maps.Point("-28.6133319236579","153.483700840369"), 
            // extents: { Bottom: "-28.7656315313798", Top: "-28.461032315936", Left: "153.328569901365", Right: "153.638831779374"}});
        
            
        // lgas.push( {
            // Id: 97, Name: "CABONNE", AbsCode: "1400", Centroid: new google.maps.Point("-33.1108806561547","148.826923881613"), 
            // extents: { Bottom: "-33.6144043966098", Top: "-32.6073569156997", Left: "148.290184123013", Right: "149.363663640212"}});
        
            
        // lgas.push( {
            // Id: 100, Name: "CAMDEN", AbsCode: "1450", Centroid: new google.maps.Point("-34.0161139503621","150.725220081839"), 
            // extents: { Bottom: "-34.0933846617987", Top: "-33.9388432389254", Left: "150.627874766246", Right: "150.822565397432"}});
        
            
        // lgas.push( {
            // Id: 108, Name: "CAMPBELLTOWN", AbsCode: "1500", Centroid: new google.maps.Point("-34.073910433635","150.839020651324"), 
            // extents: { Bottom: "-34.1896127517704", Top: "-33.9582081154996", Left: "150.737143340276", Right: "150.940897962373"}});
        
            
        // lgas.push( {
            // Id: 7, Name: "CANADA BAY", AbsCode: "1520", Centroid: new google.maps.Point("-33.8485131832175","151.121361054476"), 
            // extents: { Bottom: "-33.874190140282", Top: "-33.822836226153", Left: "151.078017272011", Right: "151.164704836942"}});
        
            
        // lgas.push( {
            // Id: 4, Name: "CANTERBURY", AbsCode: "1550", Centroid: new google.maps.Point("-33.9222921063051","151.096867746124"), 
            // extents: { Bottom: "-33.9502404193163", Top: "-33.8943437932938", Left: "151.038493982552", Right: "151.155241509695"}});
        
            
        // lgas.push( {
            // Id: 80, Name: "CARRATHOOL", AbsCode: "1600", Centroid: new google.maps.Point("-33.6256007928174","145.425273897988"), 
            // extents: { Bottom: "-34.5799478353746", Top: "-32.6712537502602", Left: "144.357739014302", Right: "146.492808781674"}});
        
            
        // lgas.push( {
            // Id: 72, Name: "CENTRAL DARLING", AbsCode: "1700", Centroid: new google.maps.Point("-31.7715486858124","143.625689141802"), 
            // extents: { Bottom: "-33.3004632620611", Top: "-30.2426341095638", Left: "142.091828470087", Right: "145.159549813517"}});
        
            
        // lgas.push( {
            // Id: 25, Name: "CESSNOCK", AbsCode: "1720", Centroid: new google.maps.Point("-32.8938433701581","151.212424901571"), 
            // extents: { Bottom: "-33.1388598904143", Top: "-32.6488268499019", Left: "150.801289740998", Right: "151.623560062143"}});
        
            
        // lgas.push( {
            // Id: 11, Name: "CLARENCE VALLEY", AbsCode: "1730", Centroid: new google.maps.Point("-29.6944354401268","152.77244197329"), 
            // extents: { Bottom: "-30.4188967883064", Top: "-28.9699740919471", Left: "152.168124935863", Right: "153.376759010718"}});
        
            
        // lgas.push( {
            // Id: 41, Name: "COBAR", AbsCode: "1750", Centroid: new google.maps.Point("-31.9661087745602","145.360961157175"), 
            // extents: { Bottom: "-33.2761779942328", Top: "-30.6560395548875", Left: "144.016325558551", Right: "146.705596755799"}});
        
            
        // lgas.push( {
            // Id: 112, Name: "COFFS HARBOUR", AbsCode: "1800", Centroid: new google.maps.Point("-30.1732034562023","153.028816160714"), 
            // extents: { Bottom: "-30.4491448506705", Top: "-29.897262061734", Left: "152.795786780697", Right: "153.261845540732"}});
        
            
        // lgas.push( {
            // Id: 28, Name: "CONARGO", AbsCode: "1860", Centroid: new google.maps.Point("-35.2338454562555","144.823507332428"), 
            // extents: { Bottom: "-35.7273536165965", Top: "-34.7403372959144", Left: "144.048650333005", Right: "145.598364331851"}});
        
            
        // lgas.push( {
            // Id: 128, Name: "COOLAMON", AbsCode: "2000", Centroid: new google.maps.Point("-34.6004051476837","147.137054773466"), 
            // extents: { Bottom: "-34.9072662934496", Top: "-34.2935440019178", Left: "146.791765234588", Right: "147.482344312344"}});
        
            
        // lgas.push( {
            // Id: 50, Name: "COOMA-MONARO", AbsCode: "2050", Centroid: new google.maps.Point("-36.1346245713156","149.07064648276"), 
            // extents: { Bottom: "-36.6899367279279", Top: "-35.5793124147032", Left: "148.538690347493", Right: "149.602602618027"}});
        
            
        // lgas.push( {
            // Id: 91, Name: "COONAMBLE", AbsCode: "2150", Centroid: new google.maps.Point("-30.8671392889155","148.290550041633"), 
            // extents: { Bottom: "-31.4161208320094", Top: "-30.3181577458216", Left: "147.513860930236", Right: "149.067239153029"}});
        
            
        // lgas.push( {
            // Id: 107, Name: "COOTAMUNDRA", AbsCode: "2200", Centroid: new google.maps.Point("-34.5875694547082","148.020423594299"), 
            // extents: { Bottom: "-34.8386276121844", Top: "-34.336511297232", Left: "147.765529494398", Right: "148.2753176942"}});
        
            
        // lgas.push( {
            // Id: 132, Name: "COROWA", AbsCode: "2300", Centroid: new google.maps.Point("-35.8170396999964","146.327477788334"), 
            // extents: { Bottom: "-36.0510606754581", Top: "-35.5830187245347", Left: "145.944082765788", Right: "146.71087281088"}});
        
            
        // lgas.push( {
            // Id: 113, Name: "COWRA", AbsCode: "2350", Centroid: new google.maps.Point("-33.81137878989","148.783444760981"), 
            // extents: { Bottom: "-34.0972460126246", Top: "-33.5255115671554", Left: "148.309146651355", Right: "149.257742870606"}});
        
            
        // lgas.push( {
            // Id: 21, Name: "DENILIQUIN", AbsCode: "2500", Centroid: new google.maps.Point("-35.5290807908439","144.973165860375"), 
            // extents: { Bottom: "-35.5874815494404", Top: "-35.4706800322474", Left: "144.895128186345", Right: "145.051203534404"}});
        
            
        // lgas.push( {
            // Id: 58, Name: "DUBBO", AbsCode: "2600", Centroid: new google.maps.Point("-32.2699036655435","148.695717051691"), 
            // extents: { Bottom: "-32.6536171558813", Top: "-31.8861901752056", Left: "148.359790161121", Right: "149.031643942261"}});
        
            
        // lgas.push( {
            // Id: 92, Name: "DUNGOG", AbsCode: "2700", Centroid: new google.maps.Point("-32.3467174784483","151.611220592678"), 
            // extents: { Bottom: "-32.6509632531329", Top: "-32.0424717037636", Left: "151.303745611504", Right: "151.918695573852"}});
        
            
        // lgas.push( {
            // Id: 94, Name: "EUROBODALLA", AbsCode: "2750", Centroid: new google.maps.Point("-35.9330746624577","149.925369332999"), 
            // extents: { Bottom: "-36.3656043149443", Top: "-35.5005450099712", Left: "149.543023589107", Right: "150.307715076891"}});
        
            
        // lgas.push( {
            // Id: 90, Name: "FAIRFIELD", AbsCode: "2850", Centroid: new google.maps.Point("-33.867471269907","150.901091836941"), 
            // extents: { Bottom: "-33.9142052564204", Top: "-33.8207372833935", Left: "150.81154274108", Right: "150.990640932802"}});
        
            
        // lgas.push( {
            // Id: 18, Name: "FORBES", AbsCode: "2900", Centroid: new google.maps.Point("-33.3789044496407","147.864747959063"), 
            // extents: { Bottom: "-33.7205311315791", Top: "-33.0372777677023", Left: "147.296830232764", Right: "148.432665685362"}});
        
            
        // lgas.push( {
            // Id: 69, Name: "GILGANDRA", AbsCode: "2950", Centroid: new google.maps.Point("-31.6325515210116","148.703640918495"), 
            // extents: { Bottom: "-32.0170989707271", Top: "-31.2480040712962", Left: "148.268002934305", Right: "149.139278902686"}});
        
            
        // lgas.push( {
            // Id: 38, Name: "GLEN INNES SEVERN", AbsCode: "3010", Centroid: new google.maps.Point("-29.6310739613311","151.93956765847"), 
            // extents: { Bottom: "-30.0220552627202", Top: "-29.240092659942", Left: "151.426796896082", Right: "152.452338420858"}});
        
            
        // lgas.push( {
            // Id: 81, Name: "GLOUCESTER", AbsCode: "3050", Centroid: new google.maps.Point("-31.8878888180017","151.776143778722"), 
            // extents: { Bottom: "-32.1901617596766", Top: "-31.5856158763269", Left: "151.402846540467", Right: "152.149441016977"}});
        
            
        // lgas.push( {
            // Id: 77, Name: "GOSFORD", AbsCode: "3100", Centroid: new google.maps.Point("-33.3461979665176","151.234765330897"), 
            // extents: { Bottom: "-33.581995387638", Top: "-33.1104005453972", Left: "150.984404118368", Right: "151.485126543427"}});
        
            
        // lgas.push( {
            // Id: 141, Name: "GOULBURN MULWAREE", AbsCode: "3310", Centroid: new google.maps.Point("-34.9024957306357","149.901998019104"), 
            // extents: { Bottom: "-35.2562920684053", Top: "-34.5486993928661", Left: "149.47046597241", Right: "150.333530065798"}});
        
            
        // lgas.push( {
            // Id: 26, Name: "GREAT LAKES", AbsCode: "3400", Centroid: new google.maps.Point("-32.3835813478297","152.110386978414"), 
            // extents: { Bottom: "-32.7019566359948", Top: "-32.0652060596647", Left: "151.649608867499", Right: "152.571165089329"}});
        
            
        // lgas.push( {
            // Id: 9, Name: "GREATER HUME", AbsCode: "3370", Centroid: new google.maps.Point("-35.7568746329699","147.197311990205"), 
            // extents: { Bottom: "-36.0652720793758", Top: "-35.4484771865639", Left: "146.497045678433", Right: "147.897578301977"}});
        
            
        // lgas.push( {
            // Id: 24, Name: "GREATER TAREE", AbsCode: "3350", Centroid: new google.maps.Point("-31.8048472846205","152.279278979955"), 
            // extents: { Bottom: "-32.16728968119", Top: "-31.442404888051", Left: "151.753343394146", Right: "152.805214565764"}});
        
            
        // lgas.push( {
            // Id: 16, Name: "GRIFFITH", AbsCode: "3450", Centroid: new google.maps.Point("-34.3346443153774","146.023058641316"), 
            // extents: { Bottom: "-34.5701479465422", Top: "-34.0991406842127", Left: "145.734752709614", Right: "146.311364573018"}});
        
            
        // lgas.push( {
            // Id: 63, Name: "GUNDAGAI", AbsCode: "3500", Centroid: new google.maps.Point("-35.0264555907589","148.134479462411"), 
            // extents: { Bottom: "-35.2821414162472", Top: "-34.7707697652706", Left: "147.685864553261", Right: "148.58309437156"}});
        
            
        // lgas.push( {
            // Id: 136, Name: "GUNNEDAH", AbsCode: "3550", Centroid: new google.maps.Point("-31.0240575155118","150.115476963684"), 
            // extents: { Bottom: "-31.4582631453716", Top: "-30.589851885652", Left: "149.631554964005", Right: "150.599398963362"}});
        
            
        // lgas.push( {
            // Id: 122, Name: "GUYRA", AbsCode: "3650", Centroid: new google.maps.Point("-30.1756676363183","151.678392650318"), 
            // extents: { Bottom: "-30.4575094987708", Top: "-29.8938257738657", Left: "150.974865699862", Right: "152.381919600774"}});
        
            
        // lgas.push( {
            // Id: 83, Name: "GWYDIR", AbsCode: "3660", Centroid: new google.maps.Point("-29.4980621578853","150.538186585275"), 
            // extents: { Bottom: "-30.3231037830218", Top: "-28.6730205327487", Left: "150.073885684087", Right: "151.002487486463"}});
        
            
        // lgas.push( {
            // Id: 35, Name: "HARDEN", AbsCode: "3700", Centroid: new google.maps.Point("-34.6286450858495","148.406010265083"), 
            // extents: { Bottom: "-34.9071899382697", Top: "-34.3501002334292", Left: "148.141632951346", Right: "148.670387578821"}});
        
            
        // lgas.push( {
            // Id: 131, Name: "HAWKESBURY", AbsCode: "3800", Centroid: new google.maps.Point("-33.340798849441","150.782985159809"), 
            // extents: { Bottom: "-33.6855284757312", Top: "-32.9960692231507", Left: "150.430443102429", Right: "151.135527217188"}});
        
            
        // lgas.push( {
            // Id: 22, Name: "HAY", AbsCode: "3850", Centroid: new google.maps.Point("-34.1573061966056","144.580278444877"), 
            // extents: { Bottom: "-34.8522992410951", Top: "-33.4623131521161", Left: "143.901103745588", Right: "145.259453144166"}});
        
            
        // lgas.push( {
            // Id: 137, Name: "HOLROYD", AbsCode: "3950", Centroid: new google.maps.Point("-33.8274942148268","150.960799019398"), 
            // extents: { Bottom: "-33.8699228197755", Top: "-33.785065609878", Left: "150.912416987272", Right: "151.009181051524"}});
        
            
        // lgas.push( {
            // Id: 56, Name: "HORNSBY", AbsCode: "4000", Centroid: new google.maps.Point("-33.5830852193368","151.128349875103"), 
            // extents: { Bottom: "-33.7869666996756", Top: "-33.3792037389979", Left: "150.9719181461", Right: "151.284781604106"}});
        
            
        // lgas.push( {
            // Id: 62, Name: "HUNTERS HILL", AbsCode: "4100", Centroid: new google.maps.Point("-33.829767134988","151.153522204925"), 
            // extents: { Bottom: "-33.8448443024837", Top: "-33.8146899674923", Left: "151.126770637", Right: "151.180273772851"}});
        
            
        // lgas.push( {
            // Id: 34, Name: "HURSTVILLE", AbsCode: "4150", Centroid: new google.maps.Point("-33.9669642026695","151.075849759289"), 
            // extents: { Bottom: "-33.9950941035013", Top: "-33.9388343018376", Left: "151.032883328998", Right: "151.118816189579"}});
        
            
        // lgas.push( {
            // Id: 117, Name: "INVERELL", AbsCode: "4200", Centroid: new google.maps.Point("-29.3152581179773","151.04186740603"), 
            // extents: { Bottom: "-29.9970924704076", Top: "-28.633423765547", Left: "150.47911293209", Right: "151.60462187997"}});
        
            
        // lgas.push( {
            // Id: 12, Name: "JERILDERIE", AbsCode: "4250", Centroid: new google.maps.Point("-35.2228082827389","145.782716984425"), 
            // extents: { Bottom: "-35.5882088060847", Top: "-34.857407759393", Left: "145.484517763704", Right: "146.080916205147"}});
        
            
        // lgas.push( {
            // Id: 33, Name: "JUNEE", AbsCode: "4300", Centroid: new google.maps.Point("-34.8150305807963","147.630439826991"), 
            // extents: { Bottom: "-35.0970071315272", Top: "-34.5330540300655", Left: "147.310919726557", Right: "147.949959927425"}});
        
            
        // lgas.push( {
            // Id: 84, Name: "KEMPSEY", AbsCode: "4350", Centroid: new google.maps.Point("-30.9055524208518","152.680105492058"), 
            // extents: { Bottom: "-31.3105317924037", Top: "-30.5005730492999", Left: "152.269824683917", Right: "153.090386300199"}});
        
            
        // lgas.push( {
            // Id: 48, Name: "KIAMA", AbsCode: "4400", Centroid: new google.maps.Point("-34.6957709117636","150.733426775651"), 
            // extents: { Bottom: "-34.7925926816348", Top: "-34.5989491418925", Left: "150.599884156696", Right: "150.866969394606"}});
        
            
        // lgas.push( {
            // Id: 110, Name: "KOGARAH", AbsCode: "4450", Centroid: new google.maps.Point("-33.9819834897476","151.104328027266"), 
            // extents: { Bottom: "-34.0078104590139", Top: "-33.9561565204814", Left: "151.07061263029", Right: "151.138043424243"}});
        
            
        // lgas.push( {
            // Id: 31, Name: "KU-RING-GAI", AbsCode: "4500", Centroid: new google.maps.Point("-33.7272053523708","151.14859973851"), 
            // extents: { Bottom: "-33.7944300106442", Top: "-33.6599806940974", Left: "151.089783822154", Right: "151.207415654866"}});
        
            
        // lgas.push( {
            // Id: 42, Name: "KYOGLE", AbsCode: "4550", Centroid: new google.maps.Point("-28.6463520833257","152.778566746941"), 
            // extents: { Bottom: "-29.0231486800109", Top: "-28.2695554866405", Left: "152.373359439311", Right: "153.183774054571"}});
        
            
        // lgas.push( {
            // Id: 52, Name: "LACHLAN", AbsCode: "4600", Centroid: new google.maps.Point("-32.8000675785473","146.875528153208"), 
            // extents: { Bottom: "-33.6074000772624", Top: "-31.9927350798321", Left: "146.053962844591", Right: "147.697093461824"}});
        
            
        // lgas.push( {
            // Id: 19, Name: "LAKE MACQUARIE", AbsCode: "4650", Centroid: new google.maps.Point("-33.037570190651","151.535280270302"), 
            // extents: { Bottom: "-33.202864893319", Top: "-32.8722754879829", Left: "151.331442785176", Right: "151.739117755428"}});
        
            
        // lgas.push( {
            // Id: 93, Name: "LANE COVE", AbsCode: "4700", Centroid: new google.maps.Point("-33.82319594618","151.170182841523"), 
            // extents: { Bottom: "-33.8433377443285", Top: "-33.8030541480314", Left: "151.142100082261", Right: "151.198265600785"}});
        
            
        // lgas.push( {
            // Id: 147, Name: "LEETON", AbsCode: "4750", Centroid: new google.maps.Point("-34.5296030098964","146.269174499571"), 
            // extents: { Bottom: "-34.7249504991108", Top: "-34.3342555206821", Left: "146.021630495574", Right: "146.516718503569"}});
        
            
        // lgas.push( {
            // Id: 40, Name: "LEICHHARDT", AbsCode: "4800", Centroid: new google.maps.Point("-33.868044287833","151.170597645205"), 
            // extents: { Bottom: "-33.8901007293165", Top: "-33.8459878463494", Left: "151.144621712523", Right: "151.196573577888"}});
        
            
        // lgas.push( {
            // Id: 116, Name: "LISMORE", AbsCode: "4850", Centroid: new google.maps.Point("-28.7936269680328","153.261814574664"), 
            // extents: { Bottom: "-29.0714398532374", Top: "-28.5158140828282", Left: "153.073587201488", Right: "153.45004194784"}});
        
            
        // lgas.push( {
            // Id: 144, Name: "LITHGOW", AbsCode: "4870", Centroid: new google.maps.Point("-33.3202499611143","150.185729591582"), 
            // extents: { Bottom: "-33.7606540395745", Top: "-32.879845882654", Left: "149.751677877737", Right: "150.619781305427"}});
        
            
        // lgas.push( {
            // Id: 99, Name: "LIVERPOOL", AbsCode: "4900", Centroid: new google.maps.Point("-33.9525461162697","150.805816384958"), 
            // extents: { Bottom: "-34.0410944151609", Top: "-33.8639978173785", Left: "150.61767108598", Right: "150.993961683935"}});
        
            
        // lgas.push( {
            // Id: 123, Name: "LIVERPOOL PLAINS", AbsCode: "4920", Centroid: new google.maps.Point("-31.4702418998072","150.420003499179"), 
            // extents: { Bottom: "-31.8582385858597", Top: "-31.0822452137547", Left: "149.840889322889", Right: "150.999117675469"}});
        
            
        // lgas.push( {
            // Id: 152, Name: "LOCKHART", AbsCode: "4950", Centroid: new google.maps.Point("-35.3219941185177","146.793540945201"), 
            // extents: { Bottom: "-35.5758608611774", Top: "-35.068127375858", Left: "146.354225627244", Right: "147.232856263158"}});
        
            
        // lgas.push( {
            // Id: 104, Name: "MAITLAND", AbsCode: "5050", Centroid: new google.maps.Point("-32.7080962226219","151.571899916054"), 
            // extents: { Bottom: "-32.8104932024707", Top: "-32.6056992427732", Left: "151.399184145172", Right: "151.744615686936"}});
        
            
        // lgas.push( {
            // Id: 96, Name: "MANLY", AbsCode: "5150", Centroid: new google.maps.Point("-33.800871342759","151.269758933126"), 
            // extents: { Bottom: "-33.8238714761789", Top: "-33.7778712093391", Left: "151.23205430967", Right: "151.307463556582"}});
        
            
        // lgas.push( {
            // Id: 135, Name: "MARRICKVILLE", AbsCode: "5200", Centroid: new google.maps.Point("-33.9102977993645","151.155016128351"), 
            // extents: { Bottom: "-33.9337913819438", Top: "-33.8868042167852", Left: "151.128507282588", Right: "151.181524974114"}});
        
            
        // lgas.push( {
            // Id: 102, Name: "MID-WESTERN REGIONAL", AbsCode: "5270", Centroid: new google.maps.Point("-32.6019233679767","149.767482705256"), 
            // extents: { Bottom: "-33.1457569467796", Top: "-32.0580897891737", Left: "149.172327106164", Right: "150.362638304348"}});
        
            
        // lgas.push( {
            // Id: 121, Name: "MOREE PLAINS", AbsCode: "5300", Centroid: new google.maps.Point("-29.3213106195408","149.581195495866"), 
            // extents: { Bottom: "-30.1054641355113", Top: "-28.5371571035703", Left: "148.676143242676", Right: "150.486247749055"}});
        
            
        // lgas.push( {
            // Id: 105, Name: "MOSMAN", AbsCode: "5350", Centroid: new google.maps.Point("-33.8283336422321","151.248618341458"), 
            // extents: { Bottom: "-33.8533265221429", Top: "-33.8033407623212", Left: "151.228368285325", Right: "151.26886839759"}});
        
            
        // lgas.push( {
            // Id: 23, Name: "MURRAY", AbsCode: "5500", Centroid: new google.maps.Point("-35.707780865331","144.833628656129"), 
            // extents: { Bottom: "-36.1293289389036", Top: "-35.2862327917584", Left: "144.303533792662", Right: "145.363723519597"}});
        
            
        // lgas.push( {
            // Id: 148, Name: "MURRUMBIDGEE", AbsCode: "5550", Centroid: new google.maps.Point("-34.6713888195077","145.703803601283"), 
            // extents: { Bottom: "-34.9078191110472", Top: "-34.4349585279682", Left: "145.177957579077", Right: "146.229649623488"}});
        
            
        // lgas.push( {
            // Id: 13, Name: "MUSWELLBROOK", AbsCode: "5650", Centroid: new google.maps.Point("-32.4879766721749","150.698388931701"), 
            // extents: { Bottom: "-32.8459139831559", Top: "-32.1300393611938", Left: "150.226440267732", Right: "151.17033759567"}});
        
            
        // lgas.push( {
            // Id: 89, Name: "NAMBUCCA", AbsCode: "5700", Centroid: new google.maps.Point("-30.7082903913882","152.710186008676"), 
            // extents: { Bottom: "-30.9278476000092", Top: "-30.4887331827671", Left: "152.400131073105", Right: "153.020240944247"}});
        
            
        // lgas.push( {
            // Id: 95, Name: "NARRABRI", AbsCode: "5750", Centroid: new google.maps.Point("-30.3277930559849","149.602239715787"), 
            // extents: { Bottom: "-30.9063197778597", Top: "-29.7492663341102", Left: "148.810719937375", Right: "150.393759494199"}});
        
            
        // lgas.push( {
            // Id: 86, Name: "NARRANDERA", AbsCode: "5800", Centroid: new google.maps.Point("-34.5910740256925","146.531976174244"), 
            // extents: { Bottom: "-35.098405162779", Top: "-34.0837428886059", Left: "146.152383945326", Right: "146.911568403162"}});
        
            
        // lgas.push( {
            // Id: 70, Name: "NARROMINE", AbsCode: "5850", Centroid: new google.maps.Point("-32.2317373298986","147.975803757937"), 
            // extents: { Bottom: "-32.6921040210333", Top: "-31.7713706387639", Left: "147.494710525842", Right: "148.456896990032"}});
        
            
        // lgas.push( {
            // Id: 45, Name: "NEWCASTLE", AbsCode: "5900", Centroid: new google.maps.Point("-32.8761964896122","151.712341397616"), 
            // extents: { Bottom: "-32.9630402294807", Top: "-32.7893527497438", Left: "151.606749390933", Right: "151.817933404299"}});
        
            
        // lgas.push( {
            // Id: 8, Name: "NORTH SYDNEY", AbsCode: "5950", Centroid: new google.maps.Point("-33.834210799387","151.210187241223"), 
            // extents: { Bottom: "-33.8526703140025", Top: "-33.8157512847714", Left: "151.187351901608", Right: "151.233022580839"}});
        
            
        // lgas.push( {
            // Id: 64, Name: "OBERON", AbsCode: "6100", Centroid: new google.maps.Point("-33.8453430896931","149.817843925853"), 
            // extents: { Bottom: "-34.2059298393605", Top: "-33.4847563400257", Left: "149.394720504544", Right: "150.240967347162"}});
        
            
        // lgas.push( {
            // Id: 76, Name: "ORANGE", AbsCode: "6150", Centroid: new google.maps.Point("-33.3283304510553","149.122950350165"), 
            // extents: { Bottom: "-33.4392837199283", Top: "-33.2173771821823", Left: "149.009447192083", Right: "149.236453508247"}});
        
            
        // lgas.push( {
            // Id: 139, Name: "PALERANG", AbsCode: "6180", Centroid: new google.maps.Point("-35.4557072995982","149.673556130541"), 
            // extents: { Bottom: "-36.0092173825337", Top: "-34.9021972166628", Left: "149.131450461928", Right: "150.215661799154"}});
        
            
        // lgas.push( {
            // Id: 65, Name: "PARKES", AbsCode: "6200", Centroid: new google.maps.Point("-32.8232672904951","148.025137742306"), 
            // extents: { Bottom: "-33.2773161148907", Top: "-32.3692184660995", Left: "147.543019600136", Right: "148.507255884476"}});
        
            
        // lgas.push( {
            // Id: 51, Name: "PARRAMATTA", AbsCode: "6250", Centroid: new google.maps.Point("-33.8231993256003","151.0158928538"), 
            // extents: { Bottom: "-33.8783437795968", Top: "-33.7680548716039", Left: "150.947579282636", Right: "151.084206424965"}});
        
            
        // lgas.push( {
            // Id: 73, Name: "PENRITH", AbsCode: "6350", Centroid: new google.maps.Point("-33.7477283751338","150.705943903845"), 
            // extents: { Bottom: "-33.8831753557391", Top: "-33.6122813945285", Left: "150.588388847619", Right: "150.823498960071"}});
        
            
        // lgas.push( {
            // Id: 32, Name: "PITTWATER", AbsCode: "6370", Centroid: new google.maps.Point("-33.6432144169982","151.277468852143"), 
            // extents: { Bottom: "-33.714666967713", Top: "-33.5717618662834", Left: "151.211037748108", Right: "151.343899956177"}});
        
            
        // lgas.push( {
            // Id: 114, Name: "PORT MACQUARIE-HASTINGS", AbsCode: "6380", Centroid: new google.maps.Point("-31.4210435449342","152.519103728951"), 
            // extents: { Bottom: "-31.7280868611793", Top: "-31.1140002286891", Left: "152.061818248999", Right: "152.976389208903"}});
        
            
        // lgas.push( {
            // Id: 101, Name: "PORT STEPHENS", AbsCode: "6400", Centroid: new google.maps.Point("-32.7285300851599","151.899101223877"), 
            // extents: { Bottom: "-32.878209759135", Top: "-32.5788504111849", Left: "151.593493627937", Right: "152.204708819817"}});
        
            
        // lgas.push( {
            // Id: 37, Name: "QUEANBEYAN", AbsCode: "6470", Centroid: new google.maps.Point("-35.4196463562964","149.228138968794"), 
            // extents: { Bottom: "-35.5091714421372", Top: "-35.3301212704556", Left: "149.135072223502", Right: "149.321205714085"}});
        
            
        // lgas.push( {
            // Id: 66, Name: "RANDWICK", AbsCode: "6550", Centroid: new google.maps.Point("-33.9454623402054","151.23954789648"), 
            // extents: { Bottom: "-34.0014335286118", Top: "-33.889491151799", Left: "151.207167254022", Right: "151.271928538938"}});
        
            
        // lgas.push( {
            // Id: 120, Name: "RICHMOND VALLEY", AbsCode: "6610", Centroid: new google.maps.Point("-29.0268521885987","153.087203854311"), 
            // extents: { Bottom: "-29.3430099674436", Top: "-28.7106944097538", Left: "152.689347686888", Right: "153.485060021733"}});
        
            
        // lgas.push( {
            // Id: 109, Name: "ROCKDALE", AbsCode: "6650", Centroid: new google.maps.Point("-33.9681937111013","151.143419535266"), 
            // extents: { Bottom: "-34.0101081511333", Top: "-33.9262792710692", Left: "151.10078306459", Right: "151.186056005942"}});
        
            
        // lgas.push( {
            // Id: 53, Name: "RYDE", AbsCode: "6700", Centroid: new google.maps.Point("-33.8014186878469","151.112038667884"), 
            // extents: { Bottom: "-33.8416526954078", Top: "-33.761184680286", Left: "151.065037805764", Right: "151.159039530003"}});
        
            
        // lgas.push( {
            // Id: 55, Name: "SHELLHARBOUR", AbsCode: "6900", Centroid: new google.maps.Point("-34.5865438364786","150.771219273002"), 
            // extents: { Bottom: "-34.643237996747", Top: "-34.5298496762103", Left: "150.637917166588", Right: "150.904521379415"}});
        
            
        // lgas.push( {
            // Id: 61, Name: "SHOALHAVEN", AbsCode: "6950", Centroid: new google.maps.Point("-35.1418389660113","150.413613270713"), 
            // extents: { Bottom: "-35.6446183420667", Top: "-34.639059589956", Left: "149.977300352629", Right: "150.849926188798"}});
        
            
        // lgas.push( {
            // Id: 39, Name: "SINGLETON", AbsCode: "7000", Centroid: new google.maps.Point("-32.6054662510105","150.918724657485"), 
            // extents: { Bottom: "-33.0720231304383", Top: "-32.1389093715827", Left: "150.335376896626", Right: "151.502072418343"}});
        
            
        // lgas.push( {
            // Id: 29, Name: "SNOWY RIVER", AbsCode: "7050", Centroid: new google.maps.Point("-36.3030799463653","148.685764609369"), 
            // extents: { Bottom: "-36.8893211678503", Top: "-35.7168387248803", Left: "148.200677583647", Right: "149.170851635091"}});
        
            
        // lgas.push( {
            // Id: 10, Name: "STRATHFIELD", AbsCode: "7100", Centroid: new google.maps.Point("-33.8797574709214","151.075620263478"), 
            // extents: { Bottom: "-33.9095272153239", Top: "-33.8499877265189", Left: "151.056641326571", Right: "151.094599200384"}});
        
            
        // lgas.push( {
            // Id: 126, Name: "SUTHERLAND", AbsCode: "7150", Centroid: new google.maps.Point("-34.071813951488","151.077238589016"), 
            // extents: { Bottom: "-34.1723949724552", Top: "-33.9712329305208", Left: "150.922246179355", Right: "151.232230998676"}});
        
            
        // lgas.push( {
            // Id: 46, Name: "SYDNEY", AbsCode: "7200", Centroid: new google.maps.Point("-33.8890200812151","151.20403061407"), 
            // extents: { Bottom: "-33.9243909011589", Top: "-33.8536492612712", Left: "151.174953740486", Right: "151.233107487655"}});
        
            
        // lgas.push( {
            // Id: 143, Name: "TAMWORTH REGIONAL", AbsCode: "7310", Centroid: new google.maps.Point("-30.9220435306636","150.815129659665"), 
            // extents: { Bottom: "-31.6618528392044", Top: "-30.1822342221227", Left: "150.145449966328", Right: "151.484809353002"}});
        
            
        // lgas.push( {
            // Id: 47, Name: "TEMORA", AbsCode: "7350", Centroid: new google.maps.Point("-34.3829950587317","147.479128403919"), 
            // extents: { Bottom: "-34.6598754031908", Top: "-34.1061147142725", Left: "147.082799913788", Right: "147.87545689405"}});
        
            
        // lgas.push( {
            // Id: 155, Name: "TENTERFIELD", AbsCode: "7400", Centroid: new google.maps.Point("-28.8383580608302","151.998377797542"), 
            // extents: { Bottom: "-29.427472027184", Top: "-28.2492440944764", Left: "151.365342181283", Right: "152.6314134138"}});
        
            
        // lgas.push( {
            // Id: 145, Name: "THE HILLS", AbsCode: "500", Centroid: new google.maps.Point("-33.5906334416038","150.960887117392"), 
            // extents: { Bottom: "-33.8042764046036", Top: "-33.3769904786041", Left: "150.869441507845", Right: "151.052332726938"}});
        
            
        // lgas.push( {
            // Id: 1, Name: "TUMBARUMBA", AbsCode: "7450", Centroid: new google.maps.Point("-36.1733759679933","148.128733573776"), 
            // extents: { Bottom: "-36.8060924351971", Top: "-35.5406595007895", Left: "147.687384996621", Right: "148.570082150931"}});
        
            
        // lgas.push( {
            // Id: 27, Name: "TUMUT", AbsCode: "7500", Centroid: new google.maps.Point("-35.5415377153354","148.325716491801"), 
            // extents: { Bottom: "-36.0029322253567", Top: "-35.0801432053141", Left: "147.842717517603", Right: "148.808715465999"}});
        
            
        // lgas.push( {
            // Id: 149, Name: "TWEED", AbsCode: "7550", Centroid: new google.maps.Point("-28.3498765462776","153.346565104238"), 
            // extents: { Bottom: "-28.5427325376475", Top: "-28.1570205549077", Left: "153.10719981299", Right: "153.585930395486"}});
        
            
        // lgas.push( {
            // Id: 103, Name: "UPPER HUNTER", AbsCode: "7620", Centroid: new google.maps.Point("-31.981266142783","150.692820906353"), 
            // extents: { Bottom: "-32.4087957187172", Top: "-31.5537365668488", Left: "149.791607961563", Right: "151.594033851143"}});
        
            
        // lgas.push( {
            // Id: 154, Name: "UPPER LACHLAN", AbsCode: "7640", Centroid: new google.maps.Point("-34.418820697564","149.530613980983"), 
            // extents: { Bottom: "-34.9495224762471", Top: "-33.8881189188809", Left: "148.938001476403", Right: "150.123226485563"}});
        
            
        // lgas.push( {
            // Id: 30, Name: "URALLA", AbsCode: "7650", Centroid: new google.maps.Point("-30.4854819562617","151.354395768062"), 
            // extents: { Bottom: "-30.8970279136179", Top: "-30.0739359989054", Left: "150.934266051566", Right: "151.774525484559"}});
        
            
        // lgas.push( {
            // Id: 17, Name: "URANA", AbsCode: "7700", Centroid: new google.maps.Point("-35.271737364141","146.323703957324"), 
            // extents: { Bottom: "-35.6700436219527", Top: "-34.8734311063293", Left: "145.956934835954", Right: "146.690473078695"}});
        
            
        // lgas.push( {
            // Id: 153, Name: "WAGGA WAGGA", AbsCode: "7750", Centroid: new google.maps.Point("-35.1858671124777","147.355113002977"), 
            // extents: { Bottom: "-35.5636245447105", Top: "-34.8081096802448", Left: "146.778784782303", Right: "147.931441223651"}});
        
            
        // lgas.push( {
            // Id: 74, Name: "WAKOOL", AbsCode: "7800", Centroid: new google.maps.Point("-35.050554237492","143.908310736037"), 
            // extents: { Bottom: "-35.7703818849012", Top: "-34.3307265900827", Left: "143.317444544698", Right: "144.499176927376"}});
        
            
        // lgas.push( {
            // Id: 150, Name: "WALCHA", AbsCode: "7850", Centroid: new google.maps.Point("-31.1976420260129","151.818544783577"), 
            // extents: { Bottom: "-31.6381581004366", Top: "-30.7571259515892", Left: "151.287483993021", Right: "152.349605574133"}});
        
            
        // lgas.push( {
            // Id: 119, Name: "WALGETT", AbsCode: "7900", Centroid: new google.maps.Point("-29.7903114384183","148.133925497424"), 
            // extents: { Bottom: "-30.5815720099085", Top: "-28.999050866928", Left: "147.173549559944", Right: "149.094301434904"}});
        
            
        // lgas.push( {
            // Id: 140, Name: "WARREN", AbsCode: "7950", Centroid: new google.maps.Point("-31.2955341757466","147.728530990522"), 
            // extents: { Bottom: "-32.1887502286711", Top: "-30.4023181228222", Left: "147.099597067316", Right: "148.357464913728"}});
        
            
        // lgas.push( {
            // Id: 79, Name: "WARRINGAH", AbsCode: "8000", Centroid: new google.maps.Point("-33.7031880940922","151.239029832232"), 
            // extents: { Bottom: "-33.7923797871148", Top: "-33.6139964010697", Left: "151.160580402273", Right: "151.317479262191"}});
        
            
        // lgas.push( {
            // Id: 124, Name: "WARRUMBUNGLE", AbsCode: "8020", Centroid: new google.maps.Point("-31.4551862100071","149.453779219258"), 
            // extents: { Bottom: "-32.3047464398479", Top: "-30.6056259801664", Left: "148.796920033914", Right: "150.110638404601"}});
        
            
        // lgas.push( {
            // Id: 68, Name: "WAVERLEY", AbsCode: "8050", Centroid: new google.maps.Point("-33.8840674778997","151.263521441912"), 
            // extents: { Bottom: "-33.9111068119864", Top: "-33.857028143813", Left: "151.240787395268", Right: "151.286255488555"}});
        
            
        // lgas.push( {
            // Id: 20, Name: "WEDDIN", AbsCode: "8100", Centroid: new google.maps.Point("-33.8784577610818","148.011454052361"), 
            // extents: { Bottom: "-34.1431911599526", Top: "-33.613724362211", Left: "147.5070415109", Right: "148.515866593822"}});
        
            
        // lgas.push( {
            // Id: 98, Name: "WELLINGTON", AbsCode: "8150", Centroid: new google.maps.Point("-32.5751469429351","148.973855678957"), 
            // extents: { Bottom: "-33.0630746762623", Top: "-32.0872192096079", Left: "148.644501693798", Right: "149.303209664117"}});
        
            
        // lgas.push( {
            // Id: 82, Name: "WENTWORTH", AbsCode: "8200", Centroid: new google.maps.Point("-33.6710573494631","142.336994079255"), 
            // extents: { Bottom: "-34.5935777190314", Top: "-32.7485369798947", Left: "141.002180900427", Right: "143.671807258084"}});
        
            
        // lgas.push( {
            // Id: 2, Name: "WILLOUGHBY", AbsCode: "8250", Centroid: new google.maps.Point("-33.8016725477545","151.188188662229"), 
            // extents: { Bottom: "-33.824776649894", Top: "-33.7785684456151", Left: "151.143436064844", Right: "151.232941259614"}});
        
            
        // lgas.push( {
            // Id: 49, Name: "WINGECARRIBEE", AbsCode: "8350", Centroid: new google.maps.Point("-34.4909474023067","150.354857919487"), 
            // extents: { Bottom: "-34.7693147589632", Top: "-34.2125800456503", Left: "149.964039507056", Right: "150.745676331917"}});
        
            
        // lgas.push( {
            // Id: 115, Name: "WOLLONDILLY", AbsCode: "8400", Centroid: new google.maps.Point("-34.0695791282408","150.462719928555"), 
            // extents: { Bottom: "-34.3374159062164", Top: "-33.8017423502652", Left: "150.014678014922", Right: "150.910761842188"}});
        
            
        // lgas.push( {
            // Id: 134, Name: "WOLLONGONG", AbsCode: "8450", Centroid: new google.maps.Point("-34.3418745583895","150.850836960519"), 
            // extents: { Bottom: "-34.5537370060838", Top: "-34.1300121106952", Left: "150.635094292821", Right: "151.066579628216"}});
        
            
        // lgas.push( {
            // Id: 15, Name: "WOOLLAHRA", AbsCode: "8500", Centroid: new google.maps.Point("-33.8618640316295","151.253495432207"), 
            // extents: { Bottom: "-33.890885113404", Top: "-33.832842949855", Left: "151.219206120907", Right: "151.287784743507"}});
        
            
        // lgas.push( {
            // Id: 78, Name: "WYONG", AbsCode: "8550", Centroid: new google.maps.Point("-33.2231025522673","151.415228454375"), 
            // extents: { Bottom: "-33.402485838884", Top: "-33.0437192656507", Left: "151.199163370253", Right: "151.631293538498"}});
        
            
        // lgas.push( {
            // Id: 36, Name: "YASS VALLEY", AbsCode: "8710", Centroid: new google.maps.Point("-34.9262531321042","148.972505627407"), 
            // extents: { Bottom: "-35.3169277981547", Top: "-34.5355784660537", Left: "148.522266723003", Right: "149.422744531811"}});
        
            
        // lgas.push( {
            // Id: 71, Name: "YOUNG", AbsCode: "8750", Centroid: new google.maps.Point("-34.2393378390599","148.190673432607"), 
            // extents: { Bottom: "-34.4667677032685", Top: "-34.0119079748512", Left: "147.710873635566", Right: "148.670473229648"}});
        

        gmeAccessToken = 'ya29.pgC6WNdYsBb5S7qFbmnEO2GuYuQyzM069paGFClOuR7axxU9qDB5XzU_';
        gmeAccessTokenRetrieved = new Date();
        gmeAccessTokenCachePeriodInMinutes = 20;

