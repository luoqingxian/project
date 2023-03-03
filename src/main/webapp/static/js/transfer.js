$ = layui.$

Transfer = function () {
    var $ = layui.$
    var eleId, allTree, leftTree, rightTree, tempLeftTree, tempRightTree, isRight,spread
}
Transfer.prototype.layuiTree = layui.tree
Transfer.prototype.layuiForm = layui.form
// dom 主干 绘制
Transfer.prototype.render = function (option) {
    this.eleId = option.elem
    this.spread = option.spread
    this.isRight = option.isRight
    this.allTree = this.addIdForData(option.data, 'N')
    $(this.eleId).empty()
    // 外层div
    $(option.elem).append('<div class="layui-transfer layui-form layui-border-box" lay-filter="' + this.eleId + '"></div>')
    $(option.elem).find('.layui-transfer').append('<div class="layui-transfer-box"></div>')
    $(option.elem).find('.layui-transfer').append('<div class="layui-transfer-active"></div>')
    $(option.elem).find('.layui-transfer').append('<div class="layui-transfer-box"></div>')
    // box 内容
    this.addBox(option, 0)
    this.addBox(option, 1)
    // 转换箭头 内容
    this.addArrow($(option.elem).find('.layui-transfer-active'))
    // 重组初始化树 数据 针对左右渲染及 value 已选初始值
    this.initTreeData(deepClone(this.allTree), option.value)

}
// 重组初始化树
Transfer.prototype.initLeftTree = []
Transfer.prototype.initRightTree = []
Transfer.prototype.initTreeData = function (allTree, historyValue) {
    var that = this
    // 比对，找到 value 对应 allTree  的 id 并添加
    $.each(historyValue, function (i, hisVal) {
        if (that.spread) {
            hisVal.spread = that.spread
        }
        // 一级添加 需求给出一级title为唯一不重复，所以后端不给 id ，由前端配置
        var flagOriginIndex
        $.each(allTree, function (j, orginData) {
            // 一级匹配不匹配直接 return
            if (orginData.title !== hisVal.title) return
            // 剩下的 一级匹配的赋予 originData 的 id
            hisVal.id = orginData.id
            // 处理二级不需要循环匹配的
            if (!orginData || !orginData.children) return
            if (!hisVal.children || !hisVal.children.length) {
                hisVal.children = deepClone(orginData.children)
            } else {
                flagOriginIndex = j
            }
        })
        if (typeof flagOriginIndex === "undefined") return
        // 需要循环进行二级匹配
        $.each(hisVal.children, function (n, hisChildData) {
            $.each(allTree[flagOriginIndex].children, function (n, orignChildData) {
                // 二级匹配不匹配直接 return
                if (orignChildData.title !== hisChildData.title) return
                // 剩下的 二级匹配的赋予 orignChildData 的 id
                hisChildData.id = orignChildData.id
            })
        })
    })
    // 整理数据完成
    // 左右重新渲染
    if (this.isRight) {
        this.initRightTree = this.RemoveChooseTree(allTree, historyValue)
        this.initLeftTree = historyValue || []
    } else {
        this.initLeftTree = this.RemoveChooseTree(allTree, historyValue)
        this.initRightTree = historyValue || []
    }
    this.rightTree = deepClone(this.initRightTree)
    this.leftTree = deepClone(this.initLeftTree)
    this.reload()
}
// dom 细节 绘制
Transfer.prototype.addBox = function (option, index) {
    var that = this
    var $ele = $(option.elem).find('.layui-transfer-box').eq(index).html('')
    var title = option.title[index]
    // var data = option.data
    var data = index ? this.rightTree : this.leftTree

    var showSearch = option.showSearch
    $ele.append('<div class="layui-transfer-header"></div>')
    $ele.find('.layui-transfer-header').append('<input type="checkbox" name="transferCheckbox' + index + option.elem + '" lay-filter="transferCheckbox' + index + option.elem + '" lay-skin="primary" title="' + title + '">')
    if (showSearch) {
        $ele.append('<div class="layui-transfer-search"><i class="layui-icon layui-icon-search"></i><input type="input"  class="layui-input ' + (index == 0 ? 'leftSearch' : 'rightSearch') + '" placeholder="关键词搜索"></div>')
    }
    this.layuiForm.render()
    $ele.append('<ul class="layui-transfer-data" style="height:267px"></ul>')
    this.layuiTree.render({
        elem: $ele.find('.layui-transfer-data')
        , data: data
        , showCheckbox: true  //是否显示复选框
        , showLine: false
        , id: that.eleId + '-' + index
        , oncheck: function (obj) {
            var parentId = this.id.split('-')[0]
            var index = this.id.split('-')[1]
            var checkedVal = that.layuiTree.getChecked(this.id)

            // 左右穿梭箭头 disable  控制
            that.controlArrow(checkedVal, parentId, index)
            // 全选 checkBox 选中状态筛选
            that.controlCheckAll(checkedVal, index)
        }
    });
    this.initCheckboxEvent()
    this.initSearchEvent()
}
// 穿梭箭头 disable 控制
Transfer.prototype.controlArrow = function (checkedVal, parentId, index) {
    if (checkedVal.length) {
        $(parentId).find(index == '0' ? '.layui-icon-next' : '.layui-icon-prev').parent().removeClass('layui-btn-disabled')
    } else {
        $(parentId).find(index == '0' ? '.layui-icon-next' : '.layui-icon-prev').parent().addClass('layui-btn-disabled')
    }
}
// 全选 checkBox 选中状态筛选
Transfer.prototype.controlCheckAll = function (checkedVal, index) {
    var setVal = {}
    if (isAll(index == 0 ? (this.tempLeftTree || this.leftTree) : (this.tempRightTree || this.rightTree), checkedVal)) {
        // 全选状态
        setVal['transferCheckbox' + index + this.eleId] = true
    } else {
        // 非全选状态
        setVal['transferCheckbox' + index + this.eleId] = false
    }
    this.layuiForm.val(this.eleId, setVal)
    // 是否全选控制
    function isAll(allVal, checkedVal) {
        // 第一层长度不一致 直接 false
        if (allVal.length !== checkedVal.length) {
            return false
        }
        // 比对2级，一个长度不一致 false
        var flag = true
        $.each(allVal, function (i, data) {
            if (checkedVal[i].children && checkedVal[i].children.length !== data.children.length) {
                flag = false
            }
        })
        return flag
    }
}
// 关键词搜索 事件绑定
Transfer.prototype.initSearchEvent = function () {
    var that = this
    $(this.eleId).find('.leftSearch').keyup(function () {
        var val = $(this).val().trim()
        that.keywordSearch(val, 0)
    });
    $(this.eleId).find('.rightSearch').keyup(function () {
        var val = $(this).val().trim()
        that.keywordSearch(val, 1)
    });
}
// 关键词搜索
Transfer.prototype.keywordSearch = function (val, index) {
    var tempTree = index == 0 ? deepClone(this.leftTree) : deepClone(this.rightTree)
    var resultTree = []
    // 还是一层一层来 第一层有直接 push
    $.each(tempTree, function (i, data) {
        if (data.title.indexOf(val) !== -1) {
            resultTree.push(data)
        } else if (data.children) {
            // 第二层筛选
            var childTemp = []
            $.each(data.children, function (j, childData) {
                if (childData.title.indexOf(val) !== -1) {
                    childTemp.push(childData)
                }
            })
            if (childTemp.length) {
                var tempObj = deepClone(data)
                if (!tempObj.spread) {
                    tempObj.spread = true
                    tempObj.spreadTemp = true
                }
                tempObj.children = childTemp
                resultTree.push(tempObj)
            }
        }
    })
    // 重新渲染
    this.layuiTree.reload(this.eleId + '-' + index, {
        data: resultTree
    });
    // 赋值
    if (index == 0) {
        this.tempLeftTree = resultTree
    } else {
        this.tempRightTree = resultTree
    }
}
// 初始化 全选 CheckBox 事件
Transfer.prototype.initCheckboxEvent = function () {
    var that = this
    transferCheckbox(0)
    transferCheckbox(1)
    function transferCheckbox(index) {
        that.layuiForm.on('checkbox(transferCheckbox' + index + that.eleId + ')', function (data) {
            //是否被选中，true或者false
            if (data.elem.checked) {
                var treeData = index == 0 ? (that.tempLeftTree || that.leftTree) : (that.tempRightTree || that.rightTree)
                var treeArr = []
                $.each(treeData, function (i, data) {
                    treeArr.push(data.id)
                    if (data.children && data.children.length) {
                        $.each(data.children, function (j, childData) {
                            treeArr.push(childData.id)
                        })
                    }
                })
                that.layuiTree.setChecked(that.eleId + '-' + index, treeArr)
            } else {
                that.layuiTree.reload(that.eleId + '-' + index)
                $(that.eleId).find(index == 0 ? '.layui-icon-next' : '.layui-icon-prev').parent().addClass('layui-btn-disabled')
            }
        });
    }
}
// 左右箭头穿梭点击事件
Transfer.prototype.addArrow = function ($ele) {
    var that = this
    $ele.append('<button type="button" class="layui-btn layui-btn-sm layui-btn-disabled"><i class="layui-icon layui-icon-next"></i></button><button type="button" class="layui-btn layui-btn-sm layui-btn-disabled"><i class="layui-icon layui-icon-prev"></i></button>')
    $ele.find('.layui-icon-next').parent().click(function () {
        // 获取左侧已选数据
        var leftChoose = that.layuiTree.getChecked(that.eleId + '-' + 0)
        if (!leftChoose.length) return
        // 整理左右数据
        // 左右重新渲染
        that.leftTree = that.RemoveChooseTree(that.leftTree, leftChoose)
        that.rightTree = that.addChooseTree(that.rightTree, leftChoose)

        that.reload(that.leftTree, that.rightTree)
    })

    $ele.find('.layui-icon-prev').parent().click(function () {
        // 获取右侧已选数据
        var rightChoose = that.layuiTree.getChecked(that.eleId + '-' + 1)
        if (!rightChoose.length) return
        // 整理左右数据
        // 左右重新渲染
        that.leftTree = that.addChooseTree(that.leftTree, rightChoose)
        that.rightTree = that.RemoveChooseTree(that.rightTree, rightChoose)

        that.reload(that.leftTree, that.rightTree)
    })
}
// reload 树界面
Transfer.prototype.reload = function (leftTree, rightTree) {
    this.layuiTree.reload(this.eleId + '-' + 0, {
        data: leftTree || this.initLeftTree
    });
    this.layuiTree.reload(this.eleId + '-' + 1, {
        data: rightTree || this.initRightTree
    });
    $(this.eleId).find('.layui-icon-next').parent().addClass('layui-btn-disabled')
    $(this.eleId).find('.layui-icon-prev').parent().addClass('layui-btn-disabled')
    // 全选按钮置 false
    var setVal = {}
    setVal['transferCheckbox0' + this.eleId] = false
    setVal['transferCheckbox1' + this.eleId] = false
    this.layuiForm.val(this.eleId, setVal)
    // 清除临时 树 data
    this.tempLeftTree = null
    this.tempRightTree = null
    // 清除关键词 input val
    $(this.eleId).find('.layui-input').val('')
}
// 删除已选树
Transfer.prototype.RemoveChooseTree = function (origin, choose) {
    // 这里暂时不做递归，只做 2 级树
    $.each(choose, function (n, data) {
        if (!data.children) {
            // 这里没有二级
            var id = data.id
            $.each(origin, function (i, originData) {
                if (!originData || originData.children) return
                // 比对，含有idArr的去除
                var isChoose = false
                if (id === originData.id) {
                    isChoose = true
                }
                if (isChoose) {
                    origin.splice(i, 1)
                    return
                }
            })
            return
        }
        $.each(data.children, function (m, dataChild) {
            var id = dataChild.id
            $.each(origin, function (i, data) {
                if (!data || !data.children) return
                $.each(data.children, function (j, dataChild) {
                    // 比对，含有idArr的去除
                    var isChoose = false
                    if (dataChild && id === dataChild.id) {
                        isChoose = true
                    }
                    if (isChoose) {
                        data.children.splice(j, 1)
                        return
                    }
                })
                if (data && !data.children.length) {
                    origin.splice(i, 1)
                }
            })
        })
    })
    return origin
}
// 插入已选树
Transfer.prototype.addChooseTree = function (origin, choose) {
    // 这里暂时不做递归，只做 2 级树
    $.each(choose, function (i, data) {
        if (data.spreadTemp) {
            data.spread = false
        }


        var flag = false
        var index = 0
        $.each(origin, function (j, originData) {

            if (originData.id === data.id) {
                flag = true
                index = j
            }
            return
        })
        //  flag = true 一级 id 相等，表示之前选择过，从二级开始 unshift 数组
        if (flag) {
            if (!data.children) return
            $.each(data.children, function (m, dataChild) {
                origin[index].children.unshift(dataChild)
            })
        } else {
            origin.unshift(data)
        }
    })
    return origin
}
// 默认数据无 id，加入 唯一标识 id
Transfer.prototype.addIdForData = function (data, parentId) {
    // 递归给树加唯一索引 Id
    var that = this

    $.each(data, function (i, ele) {
        if (parentId === 'N' && that.spread) {
            ele.spread = that.spread
        }
        ele.id = parentId + i
        if (ele.children && ele.children.length) {
            that.addIdForData(ele.children, ele.id)
        }
    })
    return data
}
// 获取穿梭后配置的值
Transfer.prototype.getTransferData = function () {
    return this.isRight ? this.leftTree : this.rightTree
}
// 对象数组深复制
function deepClone(source) {
    if (!source && typeof source !== 'object') { throw new Error('深复制传入数据错误') } var targetObj = source.constructor === Array ? [] : {}
    $.each(source, function (keys, val) {
        if (source[keys] && typeof source[keys] === 'object') { targetObj[keys] = deepClone(source[keys]) }
        else { targetObj[keys] = source[keys] }
    })
    return targetObj
}