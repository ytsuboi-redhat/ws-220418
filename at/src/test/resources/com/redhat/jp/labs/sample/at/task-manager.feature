Feature: Task管理
  各種 Sprint Planning または Product Backlog Refinement における Team の情報共有とディスカッションによる意思決定を効率化させる

  Scenario: Product Backlog Itemに紐づくタスクのname,description,estimate time（小数点第一位が1~4で入力した時）,status,memoが登録できる
    Given 以下の Product Backlog Item が登録されている
      | name         | story point |
      | TODO:item_11 | 3           |
    When  Product Backlog 画面を開く
    And   Product Backlog Itemの詳細画面を開く
    And   タスクの登録ボタンをクリックする
    And   以下の情報を登録する
      | name              | description | estimate time | status | memo |
      | item_11のタスク１ | 説明文      | 1.4           | todo   | メモ |
    And   登録ボタンをクリックする
    Then  以下の内容でタスクが登録されていること
      | name              | description | estimate time | status | memo |
      | item_11のタスク１ | 説明文      | 1.5           | todo   | メモ |

  Scenario: Product Backlog Itemに紐づくタスクのname,description,estimate time（小数点第一位が6~9で入力した時）,status,memoが登録できる
    Given 以下の Product Backlog Item が登録されている
      | name         | story point |
      | TODO:item_11 | 3           |
    When  Product Backlog画面を開く
    And   Product Backlog Itemの詳細画面を開く
    And   タスクの登録ボタンをクリックする
    And   以下の情報を登録する
      | name              | description | estimate time | status | memo |
      | item_11のタスク１ | 説明文      | 1.9           | todo   | メモ |
    And   登録ボタンをクリックする
    Then  Product Backlog Itemの詳細画面に作成したタスクが表示されていること
    And   以下の内容でタスクが登録されていること
      | name              | description | estimate time | status | memo |
      | item_11のタスク１ | 説明文      | 2.0           | todo   | メモ |

  Scenario: Product Backlog Itemに紐づくタスクのname,description,estimate time（小数点第一位が5で入力した時）,status,memoが登録できる
  Scenario: Product Backlog Itemに紐づくタスクのname,description,estimate time（小数点第一位が0で入力した時）,status,memoが登録できる


  Scenario: Product Backlog Itemに紐づくタスクのname,description,estimate time,status,memoが編集可能であること

  Scenario: Product Backlog Itemに紐づくタスクのname,description,estimate time,status,memoが照会可能であること

  Scenario: Product Backlog Itemに紐づくタスクの削除が可能であること