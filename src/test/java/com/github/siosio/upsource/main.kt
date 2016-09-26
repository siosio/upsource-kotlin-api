package com.github.siosio.upsource

fun main(args: Array<String>) {

  UpsourceClient("http://10.128.3.44:8080/", "siosio", "siosio")
      .project("nablarch-workflow") {
        +review(
            title = "ワークフロー単体のシングルプロジェクト構成に変更(バージョンは1.1に)",
//            revisions = listOf("nablarch-example-rest-4f2999478ab1f5ba4ec259ba5df767b88ff80dfb"),
            branch = "feature-14919"
        ) {

          // ito:f88f32bb-1537-46ab-900c-78440c17b84e
          // yamamoto:0ad3ed47-d9a8-4a9f-a605-a6115acef045
          +reviewer("f88f32bb-1537-46ab-900c-78440c17b84e")

//          watcher("fcd10bbc-d0a0-42b3-aaaa-cb31db7411dd")
          println(this)
        }
      }
}

