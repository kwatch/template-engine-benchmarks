-- -*- coding: utf-8 -*-

---
--- $Release: $
--- $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
--- $License: MIT License $
---

--print(package.path)
socket = require('socket')
--table = require('table')
--print(socket._VERSION)


module('benchmarker', package.seeall)


Benchmarker = {}
Benchmarker.new = function(loop)
	local this = {
		loop        = loop or 100*1000,
		_tasks      = {},
		_empty_task = null,

		empty_task =
			function(self, body)
				self._empty_task = body
			end,

		task =
			function(self, title, body)
				table.insert(self._tasks, {title=title, body=body})
			end,

		run =
			function(self)
				self:print_environment()
				print('# loop = ' .. self.loop)
				local i, j
				local tasks = {}
				if self._empty_task then
					table.insert(tasks, {title='(Empty)', body=self._empty_task})
				end
				for i = 1, #self._tasks do
					table.insert(tasks, self._tasks[i])
				end
				local cycle = os.getenv('C') or 3
				for c = 1, cycle do
					print()
					self:_run_task(self.loop, c, tasks)
				end
			end,

		_run_task =
			function(self, loop, cycle, tasks)
				print(string.format("%-35s %10s %10s", '# cycle='..cycle, 'real', 'actual'))
				empty_time = 0.0
				for j = 1, #tasks do
					local t = tasks[j]
					local start = socket.gettime()
					t.body(loop)
					local stop = socket.gettime()
					local real = stop - start
					local actual = real - empty_time
					if j == 1 and self._empty_task then
						empty_time = real
					end
					print(string.format("%-35s %10.3f %10.3f", t.title, real, actual))
				end
			end,

		print_environment =
			function(self)
				print(string.format("# %-20s %s", '_VERSION:', _VERSION))
			end,

	}
	return this
end
