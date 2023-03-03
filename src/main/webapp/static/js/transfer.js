$ = layui.$

Transfer = function () {
    var $ = layui.$
    var eleId, allTree, leftTree, rightTree, tempLeftTree, tempRightTree, isRight,spread
}
Transfer.prototype.layuiTree = layui.tree
Transfer.prototype.layuiForm = layui.form
// dom ���� ����
Transfer.prototype.render = function (option) {
    this.eleId = option.elem
    this.spread = option.spread
    this.isRight = option.isRight
    this.allTree = this.addIdForData(option.data, 'N')
    $(this.eleId).empty()
    // ���div
    $(option.elem).append('<div class="layui-transfer layui-form layui-border-box" lay-filter="' + this.eleId + '"></div>')
    $(option.elem).find('.layui-transfer').append('<div class="layui-transfer-box"></div>')
    $(option.elem).find('.layui-transfer').append('<div class="layui-transfer-active"></div>')
    $(option.elem).find('.layui-transfer').append('<div class="layui-transfer-box"></div>')
    // box ����
    this.addBox(option, 0)
    this.addBox(option, 1)
    // ת����ͷ ����
    this.addArrow($(option.elem).find('.layui-transfer-active'))
    // �����ʼ���� ���� ���������Ⱦ�� value ��ѡ��ʼֵ
    this.initTreeData(deepClone(this.allTree), option.value)

}
// �����ʼ����
Transfer.prototype.initLeftTree = []
Transfer.prototype.initRightTree = []
Transfer.prototype.initTreeData = function (allTree, historyValue) {
    var that = this
    // �ȶԣ��ҵ� value ��Ӧ allTree  �� id �����
    $.each(historyValue, function (i, hisVal) {
        if (that.spread) {
            hisVal.spread = that.spread
        }
        // һ����� �������һ��titleΪΨһ���ظ������Ժ�˲��� id ����ǰ������
        var flagOriginIndex
        $.each(allTree, function (j, orginData) {
            // һ��ƥ�䲻ƥ��ֱ�� return
            if (orginData.title !== hisVal.title) return
            // ʣ�µ� һ��ƥ��ĸ��� originData �� id
            hisVal.id = orginData.id
            // �����������Ҫѭ��ƥ���
            if (!orginData || !orginData.children) return
            if (!hisVal.children || !hisVal.children.length) {
                hisVal.children = deepClone(orginData.children)
            } else {
                flagOriginIndex = j
            }
        })
        if (typeof flagOriginIndex === "undefined") return
        // ��Ҫѭ�����ж���ƥ��
        $.each(hisVal.children, function (n, hisChildData) {
            $.each(allTree[flagOriginIndex].children, function (n, orignChildData) {
                // ����ƥ�䲻ƥ��ֱ�� return
                if (orignChildData.title !== hisChildData.title) return
                // ʣ�µ� ����ƥ��ĸ��� orignChildData �� id
                hisChildData.id = orignChildData.id
            })
        })
    })
    // �����������
    // ����������Ⱦ
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
// dom ϸ�� ����
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
        $ele.append('<div class="layui-transfer-search"><i class="layui-icon layui-icon-search"></i><input type="input"  class="layui-input ' + (index == 0 ? 'leftSearch' : 'rightSearch') + '" placeholder="�ؼ�������"></div>')
    }
    this.layuiForm.render()
    $ele.append('<ul class="layui-transfer-data" style="height:267px"></ul>')
    this.layuiTree.render({
        elem: $ele.find('.layui-transfer-data')
        , data: data
        , showCheckbox: true  //�Ƿ���ʾ��ѡ��
        , showLine: false
        , id: that.eleId + '-' + index
        , oncheck: function (obj) {
            var parentId = this.id.split('-')[0]
            var index = this.id.split('-')[1]
            var checkedVal = that.layuiTree.getChecked(this.id)

            // ���Ҵ����ͷ disable  ����
            that.controlArrow(checkedVal, parentId, index)
            // ȫѡ checkBox ѡ��״̬ɸѡ
            that.controlCheckAll(checkedVal, index)
        }
    });
    this.initCheckboxEvent()
    this.initSearchEvent()
}
// �����ͷ disable ����
Transfer.prototype.controlArrow = function (checkedVal, parentId, index) {
    if (checkedVal.length) {
        $(parentId).find(index == '0' ? '.layui-icon-next' : '.layui-icon-prev').parent().removeClass('layui-btn-disabled')
    } else {
        $(parentId).find(index == '0' ? '.layui-icon-next' : '.layui-icon-prev').parent().addClass('layui-btn-disabled')
    }
}
// ȫѡ checkBox ѡ��״̬ɸѡ
Transfer.prototype.controlCheckAll = function (checkedVal, index) {
    var setVal = {}
    if (isAll(index == 0 ? (this.tempLeftTree || this.leftTree) : (this.tempRightTree || this.rightTree), checkedVal)) {
        // ȫѡ״̬
        setVal['transferCheckbox' + index + this.eleId] = true
    } else {
        // ��ȫѡ״̬
        setVal['transferCheckbox' + index + this.eleId] = false
    }
    this.layuiForm.val(this.eleId, setVal)
    // �Ƿ�ȫѡ����
    function isAll(allVal, checkedVal) {
        // ��һ�㳤�Ȳ�һ�� ֱ�� false
        if (allVal.length !== checkedVal.length) {
            return false
        }
        // �ȶ�2����һ�����Ȳ�һ�� false
        var flag = true
        $.each(allVal, function (i, data) {
            if (checkedVal[i].children && checkedVal[i].children.length !== data.children.length) {
                flag = false
            }
        })
        return flag
    }
}
// �ؼ������� �¼���
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
// �ؼ�������
Transfer.prototype.keywordSearch = function (val, index) {
    var tempTree = index == 0 ? deepClone(this.leftTree) : deepClone(this.rightTree)
    var resultTree = []
    // ����һ��һ���� ��һ����ֱ�� push
    $.each(tempTree, function (i, data) {
        if (data.title.indexOf(val) !== -1) {
            resultTree.push(data)
        } else if (data.children) {
            // �ڶ���ɸѡ
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
    // ������Ⱦ
    this.layuiTree.reload(this.eleId + '-' + index, {
        data: resultTree
    });
    // ��ֵ
    if (index == 0) {
        this.tempLeftTree = resultTree
    } else {
        this.tempRightTree = resultTree
    }
}
// ��ʼ�� ȫѡ CheckBox �¼�
Transfer.prototype.initCheckboxEvent = function () {
    var that = this
    transferCheckbox(0)
    transferCheckbox(1)
    function transferCheckbox(index) {
        that.layuiForm.on('checkbox(transferCheckbox' + index + that.eleId + ')', function (data) {
            //�Ƿ�ѡ�У�true����false
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
// ���Ҽ�ͷ�������¼�
Transfer.prototype.addArrow = function ($ele) {
    var that = this
    $ele.append('<button type="button" class="layui-btn layui-btn-sm layui-btn-disabled"><i class="layui-icon layui-icon-next"></i></button><button type="button" class="layui-btn layui-btn-sm layui-btn-disabled"><i class="layui-icon layui-icon-prev"></i></button>')
    $ele.find('.layui-icon-next').parent().click(function () {
        // ��ȡ�����ѡ����
        var leftChoose = that.layuiTree.getChecked(that.eleId + '-' + 0)
        if (!leftChoose.length) return
        // ������������
        // ����������Ⱦ
        that.leftTree = that.RemoveChooseTree(that.leftTree, leftChoose)
        that.rightTree = that.addChooseTree(that.rightTree, leftChoose)

        that.reload(that.leftTree, that.rightTree)
    })

    $ele.find('.layui-icon-prev').parent().click(function () {
        // ��ȡ�Ҳ���ѡ����
        var rightChoose = that.layuiTree.getChecked(that.eleId + '-' + 1)
        if (!rightChoose.length) return
        // ������������
        // ����������Ⱦ
        that.leftTree = that.addChooseTree(that.leftTree, rightChoose)
        that.rightTree = that.RemoveChooseTree(that.rightTree, rightChoose)

        that.reload(that.leftTree, that.rightTree)
    })
}
// reload ������
Transfer.prototype.reload = function (leftTree, rightTree) {
    this.layuiTree.reload(this.eleId + '-' + 0, {
        data: leftTree || this.initLeftTree
    });
    this.layuiTree.reload(this.eleId + '-' + 1, {
        data: rightTree || this.initRightTree
    });
    $(this.eleId).find('.layui-icon-next').parent().addClass('layui-btn-disabled')
    $(this.eleId).find('.layui-icon-prev').parent().addClass('layui-btn-disabled')
    // ȫѡ��ť�� false
    var setVal = {}
    setVal['transferCheckbox0' + this.eleId] = false
    setVal['transferCheckbox1' + this.eleId] = false
    this.layuiForm.val(this.eleId, setVal)
    // �����ʱ �� data
    this.tempLeftTree = null
    this.tempRightTree = null
    // ����ؼ��� input val
    $(this.eleId).find('.layui-input').val('')
}
// ɾ����ѡ��
Transfer.prototype.RemoveChooseTree = function (origin, choose) {
    // ������ʱ�����ݹ飬ֻ�� 2 ����
    $.each(choose, function (n, data) {
        if (!data.children) {
            // ����û�ж���
            var id = data.id
            $.each(origin, function (i, originData) {
                if (!originData || originData.children) return
                // �ȶԣ�����idArr��ȥ��
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
                    // �ȶԣ�����idArr��ȥ��
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
// ������ѡ��
Transfer.prototype.addChooseTree = function (origin, choose) {
    // ������ʱ�����ݹ飬ֻ�� 2 ����
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
        //  flag = true һ�� id ��ȣ���ʾ֮ǰѡ������Ӷ�����ʼ unshift ����
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
// Ĭ�������� id������ Ψһ��ʶ id
Transfer.prototype.addIdForData = function (data, parentId) {
    // �ݹ������Ψһ���� Id
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
// ��ȡ��������õ�ֵ
Transfer.prototype.getTransferData = function () {
    return this.isRight ? this.leftTree : this.rightTree
}
// �����������
function deepClone(source) {
    if (!source && typeof source !== 'object') { throw new Error('��ƴ������ݴ���') } var targetObj = source.constructor === Array ? [] : {}
    $.each(source, function (keys, val) {
        if (source[keys] && typeof source[keys] === 'object') { targetObj[keys] = deepClone(source[keys]) }
        else { targetObj[keys] = source[keys] }
    })
    return targetObj
}