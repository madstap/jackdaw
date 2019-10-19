(ns jackdaw.test.journal-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [jackdaw.test.journal :as j]))

(deftest test-journal-read
  (testing "read a vector into the journal"
    (let [journal {:topics {:foo []}}]
      (is (= {:topics {:foo [1]}}
             (-> (j/journal-read journal [:topics :foo] [1])
                 (select-keys [:topics]))))))

  (testing "record `:updated-at`"
    (let [journal {:topics {:foo []}}]
      (is (= #{:updated-at :topics}
             (-> (j/journal-read journal [:topics :foo] [1])
                 keys
                 set))))))
