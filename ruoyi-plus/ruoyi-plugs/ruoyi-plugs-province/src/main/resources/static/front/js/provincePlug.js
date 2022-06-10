(function ($) {
    $.extend({
        provincePlug: {
            init: function (options) {
                var op = $.extend({
                    ctx:'/',
                    provinceId:null,
                    cityId:null,
                    countryId:null,
                    selectedProvince:null,
                    selectedCity:null,
                    selectedCountry:null,
                    addressId:null
                }, options);
                if(!op.provinceId){
                    window.alert("未指定省份元素id!");
                }
                this.initProvinceList(options);

            },
            initProvinceList:function (options) {
                var t=this.isEmpty(options.selectedProvince)?"":options.selectedProvince;
                var pUrl=options.ctx+"plugs/getPositionProvinceList?selectedProvince="+t;
                $.ajax({
                    type: "post",
                    url: pUrl,
                    data: null,
                    xhrFields: {
                        withCredentials: true
                    },
                    dataType: "json",
                    success: function(json) {
                        var rows=json.ProvinceList;
                        var html="<option value=''>--请选择--</option>";
                        $("#"+options.provinceId).empty();
                        $.each(rows,function(i,v){
                            if(v.selected=="selected"){
                                html+="<option value='"+v.id+"' selected='selected' >"+v.name+"</option>";
                            }else{
                                html+="<option value='"+v.id+"'>"+v.name+"</option>";
                            }
                        });
                        $("#"+options.provinceId).append(html);

                            $("#"+options.provinceId).change(function () {
                                var v=$(this).val();
                                if(!$.provincePlug.isEmpty(v)){
                                    options.selectedProvince=v;
                                    $.provincePlug.initCityList(options);
                                }
                                if(!$.provincePlug.isEmpty(options.addressId)){
                                    if(!$.provincePlug.isEmpty(v)){
                                        var text=$(this).find('option:selected').text();
                                        $.provincePlug.provinceName=text;
                                        $("#"+options.addressId).val($.provincePlug.provinceName);
                                    }
                                }
                            });

                        if(!$.provincePlug.isEmpty(options.selectedProvince)&&!$.provincePlug.isEmpty(options.cityId)){
                            $.provincePlug.initCityList(options);
                        }
                    }
                })
            },
            initCityList:function(options){
                var t=this.isEmpty(options.selectedCity)?"":options.selectedCity;
                var cityUrl=options.ctx+"plugs/getPositionCityByProvinceId?provinceId="+options.selectedProvince+"&selectedCity="+t;
                $.ajax({
                    type: "post",
                    url: cityUrl,
                    data: null,
                    xhrFields: {
                        withCredentials: true
                    },
                    dataType: "json",
                    success: function(json) {
                        var rows=json.CityList;
                        $("#"+options.cityId).empty();
                        var html="<option value=''>--请选择--</option>";
                        $.each(rows,function(i,v){
                            if(v.selected=="selected"){
                                html+="<option value='"+v.id+"' selected='selected'>"+v.name+"</option>";
                            }else{
                                html+="<option value='"+v.id+"'>"+v.name+"</option>";
                            }
                        });
                        $("#"+options.cityId).append(html);

                            $("#"+options.cityId).change(function () {
                                var v=$(this).val();
                                if(!$.provincePlug.isEmpty(v)){
                                    options.selectedCity=v;
                                    $.provincePlug.initCountryList(options);
                                }
                                if(!$.provincePlug.isEmpty(options.addressId)){
                                    if(!$.provincePlug.isEmpty(v)){
                                        var text=$(this).find('option:selected').text();
                                        $.provincePlug.cityName=text;
                                        $("#"+options.addressId).val($.provincePlug.provinceName+$.provincePlug.cityName);
                                    }
                                }
                            });

                        if(!$.provincePlug.isEmpty(options.selectedCity)&&!$.provincePlug.isEmpty(options.countryId)){
                            $.provincePlug.initCountryList(options);
                        }
                    }
                })
            },
            initCountryList:function(options){
                var t=this.isEmpty(options.selectedCountry)?"":options.selectedCountry;
                var countryUrl=options.ctx+"plugs/getPositionCountryByCityId?cityId="+options.selectedCity+"&selectedCountry="+t;
                $.ajax({
                    type: "post",
                    url: countryUrl,
                    data: null,
                    xhrFields: {
                        withCredentials: true
                    },
                    dataType: "json",
                    success: function(json) {
                        var rows=json.PositionList;
                        $("#"+options.countryId).empty();
                        var html="<option value=''>--请选择--</option>";
                        $.each(rows,function(i,v){
                            if(v.selected=="selected"){
                                html+="<option value='"+v.id+"' selected='selected'>"+v.name+"</option>";
                            }else{
                                html+="<option value='"+v.id+"'>"+v.name+"</option>";
                            }
                        });
                        $("#"+options.countryId).append(html);
                        if(!$.provincePlug.isEmpty(options.addressId)){
                            $("#"+options.countryId).change(function () {
                                var v=$(this).val();
                                if(!$.provincePlug.isEmpty(v)){
                                    var text=$(this).find('option:selected').text();
                                    $.provincePlug.countryName=text;
                                    $("#"+options.addressId).val($.provincePlug.provinceName+$.provincePlug.cityName+$.provincePlug.countryName);
                                }
                            });
                        }
                    }
                })
            },
            provinceName:"",
            cityName:"",
            countryName:"",
            isEmpty:function(val) {
                val = $.trim(val);
                if (val == null)
                    return true;
                if (val == undefined || val == 'undefined')
                    return true;
                if (val == "")
                    return true;
                if (val.length == 0)
                    return true;
                if (!/[^(^\s*)|(\s*$)]/.test(val))
                    return true;
                return false;
            }
        }
    });
})(jQuery);
