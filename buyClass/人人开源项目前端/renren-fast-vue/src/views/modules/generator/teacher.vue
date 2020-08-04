<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.id" placeholder="编号" clearable></el-input>
      </el-form-item>

      <el-form-item>
        <el-input v-model="dataForm.nickname" placeholder="昵称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.title" placeholder="选择职称" clearable="">
          <el-option value="助教" label="助教"></el-option>
          <el-option value="普通讲师" label="普通讲师"></el-option>
          <el-option value="精英讲师" label="精英讲师"></el-option>
          <el-option value="高级讲师" label="高级讲师"></el-option>
          <el-option value="金牌讲师" label="金牌讲师"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('generator:teacher:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('generator:teacher:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="主键"
        width="80">
      </el-table-column>
      <el-table-column
        prop="nickName"
        header-align="center"
        align="center"
        label="昵称">
      </el-table-column>
      <!-- <el-table-column
        prop="photo"
        header-align="center"
        align="center"
        label="头像">
      </el-table-column> -->
      <el-table-column
        prop="introduction"
        header-align="center"
        align="left"
        label="讲师介绍"
        width="200"
        show-overflow-tooltip="true">
      </el-table-column>
      <el-table-column
        prop="title"
        header-align="center"
        align="center"
        label="职称">
      </el-table-column>
      <el-table-column
        prop="curriculums"
        header-align="center"
        align="center"
        label="课程数量">
      </el-table-column>
      <el-table-column
        prop="rating"
        header-align="center"
        align="center"
        label="好评度">
      </el-table-column>
      <el-table-column
        prop="students"
        header-align="center"
        align="center"
        label="学生数量">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './teacher-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          id:'',
          nickname:'',
          title:''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        let data={
            'page':this.pageIndex,
            'limit':this.pageSize
        }
        if(this.dataForm.id!=null&&this.dataForm.id!='')
        {
          data.id=Number(this.dataForm.id)
        }
        if(this.dataForm.nickname!=null&&this.dataForm.nickname!='')
        {
          data.nickname=this.dataForm.nickname
        }
        if(this.dataForm.title!=null&&this.dataForm.title!='')
        {
          data.title=Number(this.dataForm.title)
        }
        this.$http({
          url: this.$http.adornUrl('/app/teacher/list'),
          method: 'post',
          data: this.$http.adornData(data)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/app/teacher/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
