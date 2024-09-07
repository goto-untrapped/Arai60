public class ValidateBinarySearchTreeStep1 {
    
    /*
     * 答えを見た 20分くらい
     * 時間計算量:O(n)
     * 空間計算量:O(n)
     * n <= 10^4 のため、Javaだとスタックオーバーフローの可能性あり。
     */
    class Solution {
        public boolean isValidBST(TreeNode root) {
            return isValidBSTHelper(root, null, null);
        }
        
        public boolean isValidBSTHelper(TreeNode node, Integer minVal, Integer maxVal) {
            if (node == null) { 
                return true;
            }

            boolean isLeftBst = true;
            if (minVal != null && node.val <= minVal) { 
                isLeftBst = false;                           
            }

            boolean isRightBst = true;
            if (maxVal != null && maxVal <= node.val) { 
                isRightBst = false;   
            }

            if (!isLeftBst || !isRightBst) {
                return false;
            }
            
            return isValidBSTHelper(node.left, minVal, node.val) && isValidBSTHelper(node.right, node.val, maxVal);
        }
    }
    
    // 2回目 11min 1回目を元に直した後に根本的に無理だと気付いた
    // 親より上のノードが比較対象になれないため
    class Solution_WA2 {
        public boolean isValidBST(TreeNode root) {
            return isValidBSTHelper(root, root.val, root.val);
        }
        
        public boolean isValidBSTHelper(TreeNode root, int minVal, int maxVal) {
            if (root == null) { 
                return true;
            }

            boolean isLeftBst = true;
            if (root.left != null) { 
                if (root.left.val >= minVal) { 
                    isLeftBst = false;                           
                }
                minVal = root.left.val;
            }
            boolean isRightBst = true;
            if (root.right != null) { 
                if (maxVal >= root.right.val) { 
                    isRightBst = false;   
                }
                maxVal = root.right.val;
            }
            
            if (!isLeftBst || !isRightBst) {
                return false;
            }
            
            return isValidBSTHelper(root.left, minVal, root.val) || isValidBSTHelper(root.right, root.val, maxVal);
        }
    }
    
    // 1回目 13min [5,3,6,null,null,4,8]のようなケースでWA
    class Solution_WA1 {
        public boolean isValidBST(TreeNode root) {
            if (root == null) { 
                return true;
            }
            boolean isLeftBst = true;
            if (root.left != null) { 
                if (root.left.val >= root.val) { 
                    isLeftBst = false;                           
                }
            }
            boolean isRightBst = true;
            if (root.right != null) { 
                if (root.val > root.right.val) { 
                    isRightBst = false;   
                }
            }
            
            if (!isLeftBst || !isRightBst) {
                return false;
            }
            
            return isValidBST(root.left) || isValidBST(root.right);
        }
    }
}
