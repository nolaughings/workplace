Vue.component('ts-frame', {
    props:["now"],
    methods:{
        openMenu: function (type) {
            if(type=='1'){
                location.href="intelligent_platform.html";
            }
            if(type=='2'){
                location.href="pension_robot.html";
            }
            if(type=='3'){
                location.href="medical_vehicle.html";
            }
            if(type=='99'){
                alert('功能正在建设中');
            }
        }
    },
    data:function () {
        return {
            zhptUrl:"http://www.tensorik.com/developer"
            // zhptUrl:"http://149.28.80.169:8087/"
        }
    },
    template: '<div class="frame"><div class="head-box"><div class="logo"><img src="/images/logo.png"></div><div class="menu-box"><a href="index.html"  class="menu" :class="now==\'0\'?\'active\':\'\'">首页</a><a href="intelligent_platform.html"  class="menu" :class="now==\'1\'?\'active\':\'\'">产品</a><a href="javascript:void(0)"  @click="openMenu(\'99\')" class="menu" :class="now==\'2\'?\'active\':\'\'">解决方案</a><a :href="zhptUrl" class="menu" :class="now==\'3\'?\'active\':\'\'">开发者平台</a><a href="api_store.html"  class="menu" :class="now==\'4\'?\'active\':\'\'">接口商城</a><a href="about.html" class="menu" :class="now==\'5\'?\'active\':\'\'">关于我们</a></div></div><div class="body-box"><slot></slot></div><div class="footer-box"><div class="footer-container"><el-row :gutter="90"><el-col :span="10"><div class="big-font">关于檀索</div><div class="small-font">上海檀索科技有限公司坐落于上海第二工业大学宝山校区创业园，是一家专业提供人工智能解决方案及产品的企业，团队拥有多年的人工智能行业经验积累，可提供定制化AI+解决方案，深挖机器视觉、图像处理，产品涉及AI算法平台、智能机器人、覆盖工业区、生活区、商业区等应用领域。</div></el-col><el-col :span="9"><div class="small-font">咨询热线</div><div class="big-font">400-888-9999</div><div class="small-font">邮箱地址</div><div class="big-font">tspartner@tansuo.com</div></el-col><el-col :span="5"><div class="footer-qrcode"></div></el-col></el-row><div class="copyright">Copyright 上海檀索科技有限公司 版权所有</div></div></div></div>'
    // template: '<div class="frame"><div class="head-box"><div class="logo">TS</div><div class="menu-box"><a href="index.html"  class="menu" :class="now==\'0\'?\'active\':\'\'">首页</a><el-popover placement="bottom" width="128" trigger="hover" :visible-arrow="false" popper-class="submenu-box"><a class="menu" :class="now==\'1\'?\'active\':\'\'" slot="reference">产品</a><div @click="openMenu(\'1\')" class="submenu">智慧平台</div><div @click="openMenu(\'2\')" class="submenu">养老机器人</div><div @click="openMenu(\'3\')" class="submenu">智能医疗车</div></el-popover><a href="javascript:void(0)"  @click="openMenu(\'99\')" class="menu" :class="now==\'2\'?\'active\':\'\'">解决方案</a><a :href="zhptUrl" class="menu" :class="now==\'3\'?\'active\':\'\'">开发者平台</a><a href="api_store.html"  class="menu" :class="now==\'4\'?\'active\':\'\'">接口商城</a><a href="about.html" class="menu" :class="now==\'5\'?\'active\':\'\'">关于我们</a></div></div><div class="body-box"><slot></slot></div><div class="footer-box"><div class="footer-container"><el-row :gutter="90"><el-col :span="10"><div class="big-font">关于檀索</div><div class="small-font">上海檀索科技有限公司坐落于上海第二工业大学宝山校区创业园，是一家专业提供人工智能解决方案及产品的企业，团队拥有多年的人工智能行业经验积累，可提供定制化AI+解决方案，深挖机器视觉、图像处理，产品涉及AI算法平台、智能机器人、覆盖工业区、生活区、商业区等应用领域。</div></el-col><el-col :span="9"><div class="small-font">咨询热线</div><div class="big-font">400-888-9999</div><div class="small-font">邮箱地址</div><div class="big-font">tspartner@tansuo.com</div></el-col><el-col :span="5"><div class="footer-qrcode"></div></el-col></el-row><div class="copyright">Copyright 上海檀索科技有限公司 版权所有</div></div></div></div>'
});
