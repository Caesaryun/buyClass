<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="昵称" prop="nickName">
      <el-input v-model="dataForm.nickName" placeholder="昵称"></el-input>
    </el-form-item>
    <el-form-item label="头像" prop="photo">
      <el-input v-model="dataForm.photo" placeholder="头像"></el-input>
    </el-form-item>
    <el-form-item label="讲师介绍" prop="introduction">
      <el-input v-model="dataForm.introduction" placeholder="讲师介绍"></el-input>
    </el-form-item>
    <el-form-item label="职称" prop="title">
      <el-input v-model="dataForm.title" placeholder="职称"></el-input>
    </el-form-item>
    <el-form-item label="课程数量" prop="curriculums">
      <el-input v-model="dataForm.curriculums" placeholder="课程数量"></el-input>
    </el-form-item>
    <el-form-item label="好评度" prop="rating">
      <el-input v-model="dataForm.rating" placeholder="好评度"></el-input>
    </el-form-item>
    <el-form-item label="学生数量" prop="students">
      <el-input v-model="dataForm.students" placeholder="学生数量"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          nickName: '',
          photo: '',
          introduction: '',
          title: '',
          curriculums: '',
          rating: '',
          students: ''
        },
        dataRule: {
          nickName: [
            { required: true, message: '昵称不能为空', trigger: 'blur' }
          ],
          photo: [
            { required: true, message: '头像不能为空', trigger: 'blur' }
          ],
          introduction: [
            { required: true, message: '讲师介绍不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '职称不能为空', trigger: 'blur' }
          ],
          curriculums: [
            { required: true, message: '课程数量不能为空', trigger: 'blur' }
          ],
          rating: [
            { required: true, message: '好评度不能为空', trigger: 'blur' }
          ],
          students: [
            { required: true, message: '学生数量不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/app/teacher/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.nickName = data.teacher.nickName
                this.dataForm.photo = data.teacher.photo
                this.dataForm.introduction = data.teacher.introduction
                this.dataForm.title = data.teacher.title
                this.dataForm.curriculums = data.teacher.curriculums
                this.dataForm.rating = data.teacher.rating
                this.dataForm.students = data.teacher.students
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/app/teacher/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'nickName': this.dataForm.nickName,
                'photo': this.dataForm.photo,
                'introduction': this.dataForm.introduction,
                'title': this.dataForm.title,
                'curriculums': this.dataForm.curriculums,
                'rating': this.dataForm.rating,
                'students': this.dataForm.students
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
