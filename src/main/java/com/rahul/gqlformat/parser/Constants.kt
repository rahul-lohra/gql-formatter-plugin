package com.rahul.gqlformat.parser

object Constants {

    //BEFORE
//    val a = """mutation LoremIpsumHistory('$promoCode': String!) {
//                LoremIpsumHistory(promoCode: $promoCode) {
//                  Success
//                }
//            }"""
    //AFTER
//    val a = """ mutation LoremIpsumHistory('$\{"$"}promoCode': String!){
//                LoremIpsumHistory(promoCode: $\{"$"}promoCode){
//                    Success
//                }
//            }"""

    const val FORMATTED = """[
    {
        "query" : "mutation LoremIpsumDetail(${"$"}lorem_ipsum_id: Int!){
            lorem_ipsum_detail(lorem_ipsum_id:${"$"}lorem_ipsum_id){
                id
                lorem_ipsum{
                    name
                    type
                    loremipsum{
                        loremipsum
                        __loremipsum
                    }
                    __loremipsum
                }
                lorem_ipsum{
                    type
                    id
                    lorem_ipsum_lorem_ipsum_attributes{
                        name
                        image  
                        lorem_ipsum_lorem_ipsum_description
                        lorem_ipsum_lorem_ipsum_fields{
                            name
                            type
                        }
                    }
                }
            }
        }
        ",
        "variables" :{
            "lorem_ipsum_id" :%s,
            "is_lorem_ipsum" :%s
        }
        ,
        "operationName" :"LoremIpsumDetail"
    }
    ,
    {
        "query" : "mutation LoremIpsumDetailNum(${"$"}lorem_ipsum_id: Int!, ${"$"}loremIpsumId: String!){
            lorem_ipsum_number(loremipsumId:${"$"}lorem_ipsum_id, loremIpsumId :${"$"}loremIpsumId){
                lorem_ipsum_id
                list{
                    label
                }
            }
        }
        ",
        "variables" :{
            "lorem_ipsum_id" :%s,
            "lorem_ipsum_Id" :"%s"
        }
        ,
        "operationName" :"LoremIpsumDetailNum"
    }
]"""
    const val promoCode = "promoCode"

    const val LOREM_IPSUM_HISTORY = """mutation LoremIpsumHistory('$promoCode': String!) {
                LoremIpsumHistory(promoCode: $promoCode) {
                  Success
                }
            }
        """

    const val COMMON_DIGITAL = "[{\n" +
            "  \"query\": \"mutation LoremIpsumDetail(\$lorem_ipsum_id: Int!,\$is_lorem_ipsum: Int!)  {\n" +
            "   lorem_ipsum_detail(lorem_ipsum_id:\$lorem_ipsum_id, is_lorem_ipsum:\$is_lorem_ipsum) {\n" +
            "        id\n" +
            "        lorem_ipsum_feature{\n" +
            "             id\n" +
            "             text\n" +
            "        }\n" +
            "        loremipsumoperator {\n" +
            "            type\n" +
            "            id\n" +
            "            lorem_ipsum_attributes {\n" +
            "                name\n" +
            "                image\n" +
            "                lorem_ipsum_product {\n" +
            "                    type\n" +
            "                    id\n" +
            "                    lorem_ipsum_attributes {\n" +
            "                        desc\n" +
            "                        detail\n" +
            "                        promo {\n" +
            "                            id\n" +
            "                            bonus_text\n" +
            "                        }\n" +
            "                        status\n" +
            "                    }\n" +
            "                }\n" +
            "                rule {\n" +
            "                    maximum_length\n" +
            "                    lorem_ipsum_product_text\n" +
            "                }\n" +
            "                lorem_ipsum_description\n" +
            "                lorem_ipsum_fields {\n" +
            "                    name\n" +
            "                    type\n" +
            "                    loremipsum {\n" +
            "                        loremipsum\n" +
            "                        error\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "   }\n" +
            "}\",\n" +
            "  \"variables\": {\n" +
            "    \"lorem_ipsum_id\": %s,\n" +
            "    \"is_lorem_ipsum\": %s\n" +
            "  },\n" +
            "  \"operationName\": \"LoremIpsumDetail\"\n" +
            "},\n" +
            "{\n" +
            "\t\"query\": \"mutation LoremIpsumNUm(\$lorem_ipsum_id: Int!, \$loremIpsumId: String!, \$lorem_ipsum_productId:String!, \$clientNum:String!)  {\\n  \\n  lorem_ipsum_number(loremipsumId:\$lorem_ipsum_id, loremIpsumId :\$loremIpsumId, lorem_ipsum_productId:\$lorem_ipsum_productId, clientNum:\$clientNum) {\\n    client_number\\n    loremipsumoperator_id\\n    lorem_ipsum_product_id\\n    lorem_ipsum_id\\n    list {\\n      client_number\\n      loremipsumoperator_id\\n      lorem_ipsum_product_id\\n      lorem_ipsum_id\\n      label\\n    }\\n  }\\n\\n}\",\n" +
            "\t\"variables\": {\n" +
            "\t\t\"lorem_ipsum_id\": %s,\n" +
            "\t\t\"loremIpsumId\": \"%s\",\n" +
            "\t\t\"lorem_ipsum_productId\": \"%s\",\n" +
            "\t\t\"clientNum\": \"%s\"\n" +
            "\t},\n" +
            "\t\"operationName\": \"LoremIpsumNUm\"\n" +
            "}]"
    const val TEST_ARRAY_SIMPLE = "" +
            "[{\n" +
            "    \"query\":\"mutation Abc(\$param:String){\n" +
            "        abc(a:\$param){\n" +
            "            id\n" +
            "            name\n" +
            "        }\n" +
            "    }\"\n" +
            "},\n" +
            "{\n" +
            "    \"query\":\"mutation Def(\$param:String){\n" +
            "        def(a:\$param){\n" +
            "            id\n" +
            "            name\n" +
            "        }\n" +
            "    }\"\n" +
            "}\n" +
            "\n" +
            "]"

    const val TEST_ARRAY = "[{\n" +
            "    \"query\":\"mutation Abc(\$param:String){\n" +
            "        abc(a:\$param){\n" +
            "            id\n" +
            "            name\n" +
            "        }\n" +
            "    }\",\n" +
            "    \"variables\":{\n" +
            "    \"params\":%s\n" +
            "    },\n" +
            "    \"operationName\":\"Abc\"\n" +
            "},\n" +
            "{\n" +
            "    \"query\":\"mutation Def(\$param:String){\n" +
            "        def(a:\$param){\n" +
            "            id\n" +
            "            name\n" +
            "        }\n" +
            "    }\",\n" +
            "    \"variables\":{\n" +
            "    \"params\":%s\n" +
            "    },\n" +
            "    \"operationName\":\"Def\"\n" +
            "}\n" +
            "\n" +
            "]"

    const val ARRAY = "[{\n" +
            "  \"query\": \"mutation LoremIpsumDetail(\$lorem_ipsum_id: Int!,\$is_lorem_ipsum: Int!)  {  \\n  lorem_ipsum_detail(lorem_ipsum_id\$lorem_ipsum_id, is_lorem_ipsum:\$is_lorem_ipsum) {\\n    id\\n    name\\n    title\\n    icon\\n    icon_url\\n    is_new\\n    instant_checkout\\n    slug\\n    microsite_url\\n    default_loremipsumoperator_id\\n    loremipsumoperator_style\\n    loremipsumoperator_label\\n    lorem_ipsum_feature {\\n   id\\n   text\\n   button_text\\n   }\\n    client_number {\\n      name\\n      type\\n      text\\n      placeholder\\n      default\\n      loremipsum {\\n        loremipsum\\n        error\\n        __loremipsum\\n      }\\n      __loremipsum\\n    }\\n    other_banners {\\n      type\\n      id\\n      lorem_ipsum_attributes {\\n        title\\n        subtitle\\n        promocode\\n        link\\n        image\\n        data_title\\n      }\\n    }\\n    banners {\\n      type\\n      id\\n      lorem_ipsum_attributes {\\n        title\\n        subtitle\\n        promocode\\n        link\\n        image\\n        data_title\\n      }\\n    }\\n   guides{\\n id\\n    type\\n  lorem_ipsum_attributes{\\n   title\\n   source_link\\n }\\n }\\n    loremipsumoperator {\\n      type\\n      id\\n      lorem_ipsum_attributes {\\n        name\\n        image\\n        lastorder_url\\n        default_lorem_ipsum_product_id\\n        prefix\\n        ussd\\n        lorem_ipsum_product {\\n          type\\n          id\\n          lorem_ipsum_attributes {\\n            desc\\n            detail\\n            detail_compact\\n            detail_url\\n            detail_url_text\\n            info\\n            price\\n            price_plain\\n            promo {\\n              id\\n              bonus_text\\n              new_price\\n              new_price_plain\\n              tag\\n              terms\\n              value_text\\n            }\\n            status\\n          }\\n        }\\n        rule {\\n          maximum_length\\n          lorem_ipsum_product_text\\n          lorem_ipsum_product_view_style\\n          show_price\\n          enable_voucher\\n          button_text\\n        }\\n        lorem_ipsum_description\\n        first_color\\n        second_color\\n        lorem_ipsum_fields {\\n          name\\n          type\\n          text\\n          placeholder\\n          default\\n          loremipsum {\\n            loremipsum\\n            error\\n          }\\n        }\\n      }\\n    }\\n  }\\n}\",\n" +
            "  \"variables\": {\n" +
            "    \"lorem_ipsum_id\": %s,\n" +
            "    \"is_lorem_ipsum\": %s\n" +
            "  },\n" +
            "  \"operationName\": \"LoremIpsumDetail\"\n" +
            "},\n" +
            "{\n" +
            "\t\"query\": \"mutation LoremIpsumNUm(\$lorem_ipsum_id: Int!,\$loremIpsumId: String!,\$lorem_ipsum_productId:String!,\$clientNum:String!)  {\\n  \\n  lorem_ipsum_number(loremipsumId:\$lorem_ipsum_id, loremIpsumId \$loremIpsumId, lorem_ipsum_productId\$lorem_ipsum_productId, clientNum:\$clientNum) {\\n    client_number\\n    loremipsumoperator_id\\n    lorem_ipsum_product_id\\n    lorem_ipsum_id\\n    list {\\n      client_number\\n      loremipsumoperator_id\\n      lorem_ipsum_product_id\\n      lorem_ipsum_id\\n      label\\n    }\\n  }\\n\\n}\",\n" +
            "\t\"variables\": {\n" +
            "\t\t\"lorem_ipsum_id\": %s,\n" +
            "\t\t\"loremIpsumId\": \"%s\",\n" +
            "\t\t\"lorem_ipsum_productId\": \"%s\",\n" +
            "\t\t\"clientNum\": \"%s\"\n" +
            "\t},\n" +
            "\t\"operationName\": \"LoremIpsumNUm\"\n" +
            "}]"
    
    val paramShopId = "shop_id"

    val query1 = """
        query getLoremIpsumList($$paramShopId: Int!) {
          getLoremIpsumList(shop_id:$$paramShopId) {
            vouchers {
              lorem_ipsum_product
              loremIpsumOwner {
                loremipsum_id
                identifier
              } 
            }
          }
        }
    """.trimIndent()

    val lorem_ipsum_id = "lorem_ipsum_id"
    val is_lorem_ipsum = "is_lorem_ipsum"
    val loremIpsumId = "loremIpsumId"
    val lorem_ipsum_productId = "lorem_ipsum_productId"

    val COMMON_DIGITAL_2 = """
        [
            {
                "query" : "mutation LoremIpsumDetail(        \$lorem_ipsum_id        : Int!,        \$is_lorem_ipsum        : Int!){
                    lorem_ipsum_detail(lorem_ipsum_id:        \$lorem_ipsum_id        , is_lorem_ipsum:        \$is_lorem_ipsum        ){
                        id
                        lorem_ipsum_feature{
                            id
                            text
                        }
                        client_number{
                            name
                            type
                            loremipsum{
                                loremipsum
                                __loremipsum
                            }
                            __loremipsum
                        }
                        loremipsumoperator{
                            type
                            id
                            lorem_ipsum_attributes{
                                name
                                image
                            }
                        }
                    }
                }
                ",
                "variables" :{
                    \"lorem_ipsum_id\" :%s,
                    \"is_lorem_ipsum\" :%s
                }
                ,
                "operationName" :"LoremIpsumDetail"
            }
            ,
            {
                "query" : "mutation LoremIpsumNUm(        \$lorem_ipsum_id        : Int!,         \$loremIpsumId        : String!,         \$lorem_ipsum_productId        :String!){
                    lorem_ipsum_number(loremipsumId:        \$lorem_ipsum_id        , loremIpsumId :        \$loremIpsumId        , lorem_ipsum_productId:        \$lorem_ipsum_productId){
                        lorem_ipsum_product_id
                        lorem_ipsum_id
                        list{
                            lorem_ipsum_product_id
                            lorem_ipsum_id
                        }
                    }
                }
                ",
                "variables" :{
                    \"lorem_ipsum_id\" :%s,
                    \"loremIpsumId\" :"%s",
                }
                ,
                "operationName" :"LoremIpsumNUm"
            }
        ]
    """.trimIndent()
}